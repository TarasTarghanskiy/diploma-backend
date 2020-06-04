package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.entity.*;
import server.repository.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/opinion")
@CrossOrigin
public class OpinionController {

    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/get")
    public ResponseEntity<Long> get(
            @RequestParam(value = "postID", required = false, defaultValue = "0") long postID,
            @RequestParam(value = "commentID", required = false, defaultValue = "0") long commentID
    ) {
        Set<OpinionAgent> opinionAgentSet;
        if (commentID != 0) {
            opinionAgentSet = opinionRepository.findAllByComment(commentRepository.getOne(commentID));
        } else {
            opinionAgentSet = opinionRepository.findAllByPost(postRepository.getOne(postID));
        }

        long l = 0;

        for (OpinionAgent opinionAgent : opinionAgentSet) {
            if (opinionAgent.isPositive()) {
                l += opinionAgent.getRating();
            } else {
                l -= opinionAgent.getRating();
            }
        }

        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @GetMapping("/create")
    public ResponseEntity<Set<String>> create(
            @RequestParam(value = "like") boolean like,
            @RequestParam(value = "serverID") long serverID,
            @RequestParam(value = "postID", required = false, defaultValue = "0") long postID,
            @RequestParam(value = "commentID", required = false, defaultValue = "0") long commentID) {
        String accountName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        AccountEntity accountEntity = accountRepository.getAccountEntityByAccountName(accountName);
        if (accountEntity == null)
            return new ResponseEntity<>(Collections.singleton("you are UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);

        ServerEntity serverEntity = serverRepository.getOne(serverID);
        UserEntity userEntity = userRepository.findUserEntityByUserServerAndUserAccount(serverEntity, accountEntity);


        OpinionAgent opinionAgent;

        if (commentID != 0) {
            CommentEntity commentEntity = commentRepository.getOne(commentID);

            if (!commentEntity.getChildCommentList().stream().map(CommentEntity::getCommentUser).map(UserEntity::getUserID).anyMatch(id -> id == userEntity.getUserID()) && !like) {
                return new ResponseEntity<>(Collections.singleton("you need to text comment before dislike this comment"), HttpStatus.UNAUTHORIZED);
            }

            opinionAgent = opinionRepository.getByCommentAndUser(commentEntity, userEntity);

            if (opinionAgent != null) {
                opinionAgent.setPositive(like);
                opinionAgent.setRating(userEntity.getReputation());
                opinionRepository.save(opinionAgent);
            } else {

                opinionAgent = new OpinionAgent(userEntity, commentEntity, like);
                commentEntity.getOpinionAgentList().add(opinionAgent);
                opinionAgent.setComment(commentEntity);

                opinionRepository.save(opinionAgent);
                commentRepository.save(commentEntity);
            }
        } else {
            PostEntity postEntity = postRepository.getOne(postID);
            if (!postEntity.getPostCommentList().stream().map(CommentEntity::getCommentUser).map(UserEntity::getUserID).anyMatch(id -> id == userEntity.getUserID()) && !like) {
                return new ResponseEntity<>(Collections.singleton("you need to text comment before dislike this post"), HttpStatus.UNAUTHORIZED);
            }

            opinionAgent = opinionRepository.getByPostAndUser(postEntity, userEntity);

            if (opinionAgent != null) {
                opinionAgent.setPositive(like);
                opinionAgent.setRating(userEntity.getReputation());
                opinionRepository.save(opinionAgent);
            } else {

                opinionAgent = new OpinionAgent(userEntity, postEntity, like);
                postEntity.getOpinionAgentList().add(opinionAgent);
                opinionAgent.setPost(postEntity);
                opinionRepository.save(opinionAgent);

                postRepository.save(postEntity);
            }
        }


        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
