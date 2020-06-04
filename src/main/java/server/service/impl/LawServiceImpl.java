package server.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.DTO.LawDTO;
import server.entity.LawEntity;
import server.entity.ServerEntity;
import server.repository.LawRepository;
import server.repository.ServerRepository;
import server.service.LawService;
import server.service.VoteService;
import server.util.ObjectMapperUtils;
import server.util.PaginationCountUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LawServiceImpl implements LawService {

    private VoteService voteService;
    private LawRepository lawRepository;
    private ServerRepository serverRepository;
    private ObjectMapperUtils omu;
    private PaginationCountUtil pcu;

    public LawServiceImpl(VoteService voteService, LawRepository lawRepository, ServerRepository serverRepository, ObjectMapperUtils omu, PaginationCountUtil pcu) {
        this.voteService = voteService;
        this.lawRepository = lawRepository;
        this.serverRepository = serverRepository;
        this.omu = omu;
        this.pcu = pcu;
    }

    @Override
    public List<String> create(LawDTO lawDTO) {
        List<String> errorList = new ArrayList<>();

        if (lawRepository.existsLawEntityByLawText(lawDTO.getLawText()))
            errorList.add("This law exist");
        if (errorList.size() > 0) return errorList;

        ServerEntity serverEntity =serverRepository.getOne(lawDTO.getServerID());

        LawEntity lawEntity = new LawEntity();
        lawEntity.setLawText(lawDTO.getLawText());
        lawEntity.setLawServer(serverEntity);
        lawEntity.setPunishmentCount(lawDTO.getPunishmentCount());
        lawEntity.setStatus("voting");

        serverEntity.getLawServerList().add(lawEntity);

        serverRepository.save(serverEntity);
        lawRepository.save(lawEntity);

        voteService.createVoteByLaw(
                serverEntity.getServerID(),
                lawEntity.getLawText(),
                lawEntity.getLawID(),
                false
        );
        return null;
    }

    @Override
    public List<LawDTO> findAllByList(int page, long serverID) {
        return omu.mapAll(lawRepository.findAllByStatusAndLawServer(
                "active",
                serverRepository.getOne(serverID),
                PageRequest.of(page, 10, Sort.by("lawID").descending())).toList(), LawDTO.class);
    }

    @Override
    public List<Integer> pageNumberList(int page, long serverID) {
        return pcu.PageNumberList(page, lawRepository.countAllByLawServer(serverRepository.getOne(serverID)));
    }
}
