package server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import server.DTO.CommentDTO;
import server.DTO.PostDTO;
import server.DTO.UserDTO;
import server.entity.*;
import server.repository.*;
import server.service.PostService;
import server.util.ObjectMapperUtils;
import server.util.PaginationCountUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaginationCountUtil pcu;

    @Autowired
    private ObjectMapperUtils omu;

    @Override
    public List<PostDTO> findAllByList(int page, long serverID) {
        List<PostDTO> postDTOList = omu.mapAll(postRepository.findAllByPostServer(serverRepository.getOne(serverID), PageRequest.of(page, 10)).toList(), PostDTO.class);
        return postDTOList;
    }

    @Override
    public void save(PostDTO postDTO, String accountName) {
        AccountEntity accountEntity = accountRepository.getAccountEntityByAccountName(accountName);
        System.out.println(accountEntity.getAccountName());
        ServerEntity serverEntity = serverRepository.getOne(postDTO.getServerID());
        System.out.println(serverEntity.getName());
        UserEntity userEntity = userRepository.findUserEntityByUserServerAndUserAccount(serverEntity, accountEntity);
        System.out.println(userEntity.getUserName());
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDTO.getTitle());
        postEntity.setText(postDTO.getText());

        postEntity.setPostUser(userEntity);
        userEntity.getUserPostList().add(postEntity);

        postEntity.setPostServer(serverEntity);
        serverEntity.getPostServerList().add(postEntity);

        postRepository.save(postEntity);
        userRepository.save(userEntity);
        serverRepository.save(serverEntity);
    }

    @Override
    public PostDTO getPostByPostID(long postID){
        PostEntity postEntity = postRepository.getOne(postID);
        PostDTO postDTO = omu.map(postEntity, PostDTO.class);
        return postDTO;
    }

    @Override
    public List<Integer> pageNumberList(int page, long serverID) {
        return pcu.PageNumberList(page, postRepository.countAllByPostServer(serverRepository.getOne(serverID)));
    }

    @Override
    public List<String> saveComment(CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setText(commentDTO.getText());
        PostEntity postEntity = postRepository.getOne(commentDTO.getPostID());
        commentEntity.setCommentPost(postEntity);
        postEntity.getPostCommentList().add(commentEntity);
        postRepository.save(postEntity);
        commentRepository.save(commentEntity);
        return new ArrayList<>();
    }
}
