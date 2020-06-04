package server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.DTO.LawDTO;
import server.entity.*;
import server.repository.*;
import server.service.TrialService;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TrialServiceImpl implements TrialService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrialRepository trialRepository;
    @Autowired
    private UserTrialRepository userTrialRepository;

    public void create(long postID, long commentID, List<LawDTO> lawDTOList, String accountName) {
        AccountEntity accuserAccount = accountRepository.getAccountEntityByAccountName(accountName);
        if (accuserAccount == null) return;

        PostEntity postEntity = postID == 0 ? null : postRepository.getOne(postID);
        CommentEntity commentEntity = commentID == 0 ? null : commentRepository.getOne(commentID);
        UserEntity defendingUser = commentEntity == null ? Objects.requireNonNull(postEntity).getPostUser() : commentEntity.getCommentUser();
        ServerEntity serverEntity = commentEntity == null ? Objects.requireNonNull(postEntity).getPostServer(): commentEntity.getCommentPost().getPostServer();
        UserEntity accuserUser = userRepository.findUserEntityByUserServerAndUserAccount(serverEntity, accuserAccount);

        for (LawDTO lawDTO : lawDTOList) {
            TrialEntity trialEntity = new TrialEntity();
            if (commentEntity != null)
            trialEntity.setEvidenceComment(commentEntity);
            if (postEntity != null)
            trialEntity.setEvidencePost(postEntity);
            trialEntity.setTrialServer(serverEntity);
            trialEntity.setUserSet(new HashSet<>());

            trialRepository.save(trialEntity);

            UserTrialAgent userTrialAgent = new UserTrialAgent(accuserUser, trialEntity, "accuser");
            userTrialRepository.save(userTrialAgent);
            trialEntity.getUserSet().add(userTrialAgent);
            userTrialAgent = new UserTrialAgent(defendingUser, trialEntity, "defending");
            userTrialRepository.save(userTrialAgent);
            trialEntity.getUserSet().add(userTrialAgent);

            trialRepository.save(trialEntity);
        }
    }
}
