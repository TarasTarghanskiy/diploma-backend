package server.service;

import server.DTO.CommentDTO;
import server.DTO.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO getPostByPostID(long postID);

    List<Integer> pageNumberList(int page, long serverID);

    List<String> saveComment(CommentDTO commentDTO);

    List<PostDTO> findAllByList(int page, long serverID);

    void save(PostDTO postDTO, String accountName);
}
