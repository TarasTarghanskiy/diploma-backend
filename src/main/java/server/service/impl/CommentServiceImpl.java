package server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.DTO.CommentDTO;
import server.DTO.StoryDTO;
import server.entity.CommentEntity;
import server.entity.PostEntity;
import server.entity.UserEntity;
import server.repository.*;
import server.service.CommentService;
import server.service.PostService;
import server.service.StoryService;
import server.service.TermService;
import server.util.ObjectMapperUtils;
import server.util.PaginationCountUtil;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private TermService termService;
    private StoryService storyService;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private AccountRepository accountRepository;
    private ServerRepository serverRepository;
    private ObjectMapperUtils omu;
    private PaginationCountUtil pcu;

    public CommentServiceImpl(CommentRepository commentRepository, TermService termService, StoryService storyService, UserRepository userRepository, PostRepository postRepository, AccountRepository accountRepository, ServerRepository serverRepository, ObjectMapperUtils omu, PaginationCountUtil pcu) {
        this.commentRepository = commentRepository;
        this.termService = termService;
        this.storyService = storyService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.serverRepository = serverRepository;
        this.omu = omu;
        this.pcu = pcu;
    }

    @Override
    public List<String> save(CommentDTO commentDTO, String accountName, long serverID) {
        List<String> errorList = new ArrayList<>();

        UserEntity userEntity = userRepository.findUserEntityByUserServerAndUserAccount(serverRepository.getOne(serverID), accountRepository.getAccountEntityByAccountName(accountName));
        if (userEntity.getStatus().equals("blocked")) {
            errorList.add("User profile is blocked");
            return errorList;
        }

        errorList = termService.checkBlockedWords(commentDTO.getText());
        if (errorList.size() != 0) return errorList;


        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setText(commentDTO.getText());
        commentEntity.setCommentUser(userEntity);

        UserEntity messageUser;

        if (commentDTO.getOwnerCommentID() != 0){
            CommentEntity ownerComment = commentRepository.getOne(commentDTO.getOwnerCommentID());
            ownerComment.getChildCommentList().add(commentEntity);
            commentEntity.setOwnerComment(ownerComment);
            messageUser = ownerComment.getCommentUser();
            commentRepository.save(ownerComment);
        } else {
            PostEntity postEntity = postRepository.getOne(commentDTO.getPostID());
            postEntity.getPostCommentList().add(commentEntity);
            commentEntity.setCommentPost(postEntity);
            messageUser = postEntity.getPostUser();
            postRepository.save(postEntity);
        }



        commentRepository.save(commentEntity);

        storyService.addStory(userEntity.getUserName() + " leave message", "/server/" + serverID + "/post/" + commentDTO.getPostID() + "?commentID=" + commentEntity.getCommentID(), messageUser.getUserAccount());

        return null;
    }

    @Override
    public List<CommentDTO> findCommentsPageByComment(int page, long commentID) {
        List<CommentEntity> commentEntityList = commentRepository.findAllByDeleteAndOwnerComment(false,commentRepository.getOne(commentID) , PageRequest.of(page, 10, Sort.by("commentID").descending())).toList();
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            CommentDTO commentDTO = omu.map(commentEntity, CommentDTO.class);
            commentDTO.setOwnerCommentID(commentEntity.getOwnerComment().getCommentID());
            commentDTO.setUserName(commentEntity.getCommentUser().getUserName());
            commentDTO.setAccountID(commentEntity.getCommentUser().getUserAccount().getAccountID());

            commentDTOList.add(commentDTO);
        }

        return commentDTOList;
    }

    @Override
    public List<CommentDTO> findCommentsPageByPost(int page, long postID) {
        List<CommentEntity> commentEntityList = commentRepository.findAllByDeleteAndCommentPost(false, postRepository.getOne(postID) ,PageRequest.of(page, 10, Sort.by("commentID").descending())).toList();
        List<CommentDTO> commentDTOList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            CommentDTO commentDTO = omu.map(commentEntity, CommentDTO.class);
            commentDTO.setPostID(commentEntity.getCommentPost().getPostID());
            commentDTO.setUserName(commentEntity.getCommentUser().getUserName());
            commentDTO.setAccountID(commentEntity.getCommentUser().getUserAccount().getAccountID());

            commentDTOList.add(commentDTO);
        }

        return commentDTOList;
    }

    @Override
    public List<Integer> pageNumberListByPost(int page, long postID) {
        return pcu.PageNumberList(page, commentRepository.countAllByCommentPostAndDelete(postRepository.getOne(postID), false));
    }

    @Override
    public List<Integer> pageNumberListByComment(int page, long commentID) {
        return pcu.PageNumberList(page, commentRepository.countAllByOwnerCommentAndDelete(commentRepository.getOne(commentID), false));
    }

    @Override
    public CommentDTO getCommentByCommentID(long commentID) {
        CommentEntity commentEntity = commentRepository.getOne(commentID);
        CommentDTO commentDTO = omu.map(commentEntity, CommentDTO.class);
        commentDTO.setUserName(commentEntity.getCommentUser().getUserName());
        if (commentEntity.getCommentPost() != null)
            commentDTO.setPostID(commentEntity.getCommentPost().getPostID());
        if (commentEntity.getOwnerComment() != null)
            commentDTO.setOwnerCommentID(commentEntity.getOwnerComment().getCommentID());
        commentDTO.setAccountID(commentEntity.getCommentUser().getUserAccount().getAccountID());
        return commentDTO;
    }
}
