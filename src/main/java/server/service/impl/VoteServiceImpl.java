package server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.DTO.VoteDTO;
import server.entity.*;
import server.repository.*;
import server.service.ServerService;
import server.service.StoryService;
import server.service.VoteService;
import server.util.ObjectMapperUtils;
import server.util.PaginationCountUtil;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private ServerService serverService;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteVariantRepository voteVariantRepository;

    @Autowired
    private StoryService storyService;

    @Autowired
    private ObjectMapperUtils omu;

    @Autowired
    private PaginationCountUtil pcu;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    LawRepository lawRepository;

    @Override
    public void pastDay() {
        List<VoteEntity> voteEntityList = voteRepository.getAllByVoteCreateDateLessThan(Date.from(Instant.now().minus(Duration.ofMinutes(0))))
                .stream().filter(VoteEntity::isVoteActive).collect(Collectors.toList());
        for (VoteEntity voteEntity : voteEntityList) {
            if (voteEntity.getVoteVariantList().stream().map(VoteVariantEntity::getVoteVariantCount).reduce((long) 0, Long::sum) >= voteEntity.getVoteMinReputation()) {
                voteEntity.setVoteActive(false);
                switch (voteEntity.getVoteType()) {
                    case "term_ban":
                    case "term_create":
                        TermEntity termEntity = termRepository.getOne(voteEntity.getSubjectID());
                        if (termEntity.getDescription() == null || termEntity.getDescription().length() == 0)
                            termEntity.setStatus("blocked");
                        else termEntity.setStatus("active");
                        termRepository.save(termEntity);
                        storyService.addStory(
                                "New term ID-" + termEntity.getTermID() + ": " + termEntity.getTerm() + " was added to server ID-" + termEntity.getTermServer().getServerID(),
                                "/server/" + termEntity.getTermServer().getServerID() + "/terms",
                                termEntity.getTermServer());
                        break;
                    case "term_delete":
                        TermEntity termEntity1 = termRepository.getOne(voteEntity.getSubjectID());
                        termEntity1.setStatus("delete");
                        termRepository.save(termEntity1);
                        storyService.addStory(
                                "New term ID-" + termEntity1.getTermID() + ": " + termEntity1.getTerm() + " was deleted from server ID-" + termEntity1.getTermServer().getServerID(),
                                "/server/" + termEntity1.getTermServer().getServerID() + "/terms",
                                termEntity1.getTermServer());
                        break;
                    case "law_create":
                        LawEntity lawEntity = lawRepository.getOne(voteEntity.getSubjectID());
                        lawEntity.setStatus("active");
                        lawRepository.save(lawEntity);
                        storyService.addStory(
                                "New term ID-" + lawEntity.getLawID() + "  was added to server ID-" + lawEntity.getLawServer().getServerID(),
                                "/server/" + lawEntity.getLawServer().getServerID() + "/terms",
                                lawEntity.getLawServer());
                        break;
                    case "law_delete":
                        LawEntity lawEntity1 = lawRepository.getOne(voteEntity.getSubjectID());
                        lawEntity1.setStatus("delete");
                        lawRepository.save(lawEntity1);
                        storyService.addStory(
                                "New term ID-" + lawEntity1.getLawID() + "  was deleted to server ID-" + lawEntity1.getLawServer().getServerID(),
                                "/server/" + lawEntity1.getLawServer().getServerID() + "/terms",
                                lawEntity1.getLawServer());
                }
            } else {
                voteEntity.setVoteMinReputation(Math.round(voteEntity.getVoteMinReputation() * 0.9));
                voteRepository.save(voteEntity);
            }
        }
    }


//    long dayDifference = ChronoUnit.DAYS.between(voteEntity.getVoteCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
//            if (dayDifference > 10 && voteEntity.getVoteVariantList().stream().map(VoteVariantEntity::getVoteVariantCount).reduce((long) 0, Long::sum) >= voteEntity.getVoteMinReputation()) {
//        System.out.println("HERE " + dayDifference);
//    } else if (dayDifference > 10) {
//        System.out.println("HERE " + dayDifference);
//    }

    @Override
    public List<VoteDTO> getVotePageList(int page, long serverID, String accountName) {
        this.pastDay();
        ServerEntity serverEntity = serverRepository.getOne(serverID);
        List<VoteEntity> voteEntityList = voteRepository.findAllByVoteServer(serverEntity, PageRequest.of(page, 10, Sort.by("voteID").descending())).toList();
        if (accountName.equals("anonymousUser"))
            return omu.mapAll(voteEntityList, VoteDTO.class);
        AccountEntity accountEntity = accountRepository.getAccountEntityByAccountName(accountName);
        UserEntity userEntity = userRepository.findUserEntityByUserServerAndUserAccount(serverEntity, accountEntity);
        if (userEntity == null)
            return omu.mapAll(voteEntityList, VoteDTO.class);
        Set<Long> voteIDList = userEntity.getVoteSet().stream().map(VoteEntity::getVoteID).collect(Collectors.toSet());
        List<VoteDTO> resultList = new ArrayList<>();
        System.out.println(voteIDList);
        for (VoteEntity voteEntity : voteEntityList) {
            VoteDTO voteDTO = omu.map(voteEntity, VoteDTO.class);
            if (voteIDList.contains(voteEntity.getVoteID())) {
                voteDTO.setVoting(true);
            }
            resultList.add(voteDTO);
        }
        return resultList;
    }

    @Override
    public List<Integer> getPageCount(int page, long serverID) {
        return pcu.PageNumberList(page, voteRepository.countAllByVoteServer(serverRepository.getOne(serverID)));
    }

    @Override
    public void createVoteByLaw(long serverID, String text, long subjectID, boolean delete) {
        ServerEntity serverEntity = serverRepository.getOne(serverID);

        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setDescription(text);
        voteEntity.setVoteMinReputation((long) (serverService.getWholeReputationCount() * 0.75));
        voteEntity.setVoteServer(serverEntity);
        serverEntity.getVoteServerList().add(voteEntity);
        voteEntity.setVoteActive(true);
        voteEntity.setSubjectID(subjectID);
        voteEntity.setVoteType(delete ? "law_delete" : "law_create");


        VoteVariantEntity voteVariantEntity = new VoteVariantEntity();
        VoteVariantEntity voteVariantEntity1 = new VoteVariantEntity();
        voteVariantEntity.setVoteVariantDescription(delete? "DELETE" : "CREATE");
        voteVariantEntity1.setVoteVariantDescription("CANCEL");

        voteVariantEntity.setVote(voteEntity);
        voteVariantEntity1.setVote(voteEntity);
        voteEntity.getVoteVariantList().add(voteVariantEntity);
        voteEntity.getVoteVariantList().add(voteVariantEntity1);

        voteVariantRepository.save(voteVariantEntity);
        voteVariantRepository.save(voteVariantEntity1);

        voteRepository.save(voteEntity);
        serverRepository.save(serverEntity);
        storyService.addStory("" +
                "New vote ID-" + voteEntity.getVoteID()
                + " by server ID-" + serverEntity.getServerID()
                + " was created", "/server/" + serverEntity.getServerID() + "/laws", serverEntity);
    }

    @Override
    public void createVoteByTerm(long serverID, String text, long subjectID, String type) {
        ServerEntity serverEntity = serverRepository.getOne(serverID);
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setDescription(text);
        voteEntity.setVoteMinReputation((long) (serverService.getWholeReputationCount() * 0.75));
        voteEntity.setVoteServer(serverEntity);
        serverEntity.getVoteServerList().add(voteEntity);
        if (type.equals("to use"))
            voteEntity.setVoteType("term_create");
        else if (type.equals("to delete")) voteEntity.setVoteType("term_delete");
        else if (type.equals("to ban")) voteEntity.setVoteType("term_ban");
        voteEntity.setVoteActive(true);
        voteEntity.setSubjectID(subjectID);

        VoteVariantEntity voteVariantEntity = new VoteVariantEntity();
        VoteVariantEntity voteVariantEntity1 = new VoteVariantEntity();
        if (type.equals("to use"))
            voteVariantEntity.setVoteVariantDescription("CREATE");
        else if (type.equals("to delete"))
            voteVariantEntity.setVoteVariantDescription("DELETE");
        else if (type.equals("to bn"))
            voteVariantEntity.setVoteVariantDescription("BANNED");
        voteVariantEntity1.setVoteVariantDescription("CANCEL");

        voteVariantEntity.setVote(voteEntity);
        voteEntity.getVoteVariantList().add(voteVariantEntity);

        voteVariantEntity1.setVote(voteEntity);
        voteEntity.getVoteVariantList().add(voteVariantEntity1);

        voteVariantRepository.save(voteVariantEntity);
        voteVariantRepository.save(voteVariantEntity1);

        voteRepository.save(voteEntity);
        serverRepository.save(serverEntity);
        storyService.addStory("" +
                "New vote ID-" + voteEntity.getVoteID()
                + " by server ID-" + serverEntity.getServerID()
                + " was created", "/server/" + serverEntity.getServerID() + "/votes", serverEntity);
    }

    @Override
    public void vote(long serverID, long voteID, long variantID, String accountName) {
        AccountEntity accountEntity = accountRepository.getAccountEntityByAccountName(accountName);
        if (accountEntity == null) return;
        ServerEntity serverEntity = serverRepository.getOne(serverID);
        UserEntity userEntity = userRepository.findUserEntityByUserServerAndUserAccount(serverEntity, accountEntity);
        VoteEntity voteEntity = voteRepository.getOne(voteID);

        if (userEntity.getVoteSet().contains(voteEntity)) {
            System.out.println("HERE!!!!"); // той п вже голосував за цей пост
            return;
        }

        VoteVariantEntity voteVariantEntity = voteEntity.getVoteVariantList().stream().filter(e -> e.getVoteVariantID() == variantID).findFirst().orElse(null);
        assert voteVariantEntity != null;
        voteVariantEntity.setVoteVariantCount(voteVariantEntity.getVoteVariantCount() + userEntity.getReputation());
        userEntity.getVoteSet().add(voteEntity);
        voteEntity.getUserSet().add(userEntity);
        voteRepository.save(voteEntity);
        userRepository.save(userEntity);
        pastDay();
    }
}
