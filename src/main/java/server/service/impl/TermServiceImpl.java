package server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.DTO.TermDTO;
import server.entity.ServerEntity;
import server.entity.TermEntity;
import server.repository.ServerRepository;
import server.repository.TermRepository;
import server.service.TermService;
import server.service.VoteService;
import server.util.ObjectMapperUtils;
import server.util.PaginationCountUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TermServiceImpl implements TermService {

    private TermRepository termRepository;
    private ServerRepository serverRepository;
    private ObjectMapperUtils omu;
    private PaginationCountUtil pcu;
    private VoteService voteService;

    public TermServiceImpl(TermRepository termRepository, ServerRepository serverRepository, ObjectMapperUtils omu, PaginationCountUtil pcu, VoteService voteService) {
        this.termRepository = termRepository;
        this.serverRepository = serverRepository;
        this.omu = omu;
        this.pcu = pcu;
        this.voteService = voteService;
    }

    @Override
    public List<String> create(TermDTO dto) {
        List<String> errorList = new ArrayList<>();

        if (dto.getTerm().length() > 30) errorList.add("max term length is 30 symbols");
        if (termRepository.existsByTerm(dto.getTerm()) && !termRepository.findFirstByTerm(dto.getTerm()).getStatus().contains("suspicious"))
            errorList.add("This term already exist");

        if (errorList.size() > 0) return errorList;

        TermEntity termEntity = new TermEntity();

        termEntity.setTerm(dto.getTerm());
        termEntity.setStatus("voting");
        termEntity.setDescription(dto.getDescription());

        ServerEntity serverEntity = serverRepository.getOne(dto.getServerID());
        serverEntity.getTermServerList().add(termEntity);
        termEntity.setTermServer(serverEntity);

        termRepository.save(termEntity);
        serverRepository.save(serverEntity);
        if (dto.isBlock())
            voteService.createVoteByTerm(dto.getServerID(), "to block this term" + dto.getTerm(), 1, "to ban");
        else
            voteService.createVoteByTerm(dto.getServerID(), dto.getTerm() + " - " + dto.getDescription(), 1, "to use");
        return null;
    }

    @Override
    public List<TermDTO> findAllByList(int page, long serverID) {
        return omu.mapAll(termRepository.findAllByTermServerAndStatusOrStatus(serverRepository.getOne(serverID), "active", "banned", PageRequest.of(page, 10, Sort.by("termID").descending())).toList(), TermDTO.class);
    }

    @Override
    public List<Integer> pageNumberList(int page, long serverID) {
        return pcu.PageNumberList(page, termRepository.countAllByTermServer(serverRepository.getOne(serverID)));
    }

    @Override
    public TermDTO getPostByPostID(long postID) {
        return null;
    }

    @Override
    public List<String> checkBlockedWords(String text) {
        List<String> errorList = new ArrayList<>();
        List<TermEntity> termEntityList = termRepository.findAllByStatus("blocked");
        for (TermEntity termEntity : termEntityList) {
            if (text.contains(termEntity.getTerm())) {
                errorList.add(termEntity.hashCode() + " is blocked on server ID-" + termEntity.getTermServer().getServerID());
            }
        }
        return errorList;
    }
}
