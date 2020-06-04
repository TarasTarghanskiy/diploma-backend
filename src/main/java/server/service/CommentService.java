package server.service;

import server.DTO.CommentDTO;

import java.util.List;

public interface CommentService {
    List<String> save(CommentDTO commentDTO, String accountName, long serverID);

    List<CommentDTO> findCommentsPageByComment(int page, long commentID);

    List<CommentDTO> findCommentsPageByPost(int page, long PostID);

    List<Integer> pageNumberListByPost(int page, long postID);

    List<Integer> pageNumberListByComment(int page, long commentID);

    CommentDTO getCommentByCommentID(long commentID);
}
