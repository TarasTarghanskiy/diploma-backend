package server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.DTO.CommentDTO;
import server.DTO.UserDTO;
import server.entity.CommentEntity;
import server.entity.PostEntity;
import server.repository.CommentRepository;
import server.repository.PostRepository;
import server.service.CommentService;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;


    @PostMapping("/create")
    public ResponseEntity<List<String>> createAccount(@RequestBody CommentDTO commentDTO, @RequestParam("serverID") long serverID) throws IOException {
        return new ResponseEntity<>(commentService.save(commentDTO, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), serverID), HttpStatus.ACCEPTED);
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<List<CommentDTO>> getHistoryList(@PathVariable int page, @RequestParam(value = "postID", required = false, defaultValue = "0") long postID, @RequestParam(value = "commentID", required = false, defaultValue = "0") long commentID) {
        if (postID != 0)
            return new ResponseEntity<>(commentService.findCommentsPageByPost(page, postID), HttpStatus.OK);
        else return new ResponseEntity<>(commentService.findCommentsPageByComment(page, commentID), HttpStatus.OK);
    }

    @GetMapping("/{commentID}")
    public ResponseEntity<CommentDTO> getHistList(@PathVariable long commentID) {
        return new ResponseEntity<>(commentService.getCommentByCommentID(commentID), HttpStatus.OK);
    }

    @GetMapping("/pages/{page}")
    public ResponseEntity<List<Integer>> getHistoryPageCount(@PathVariable int page, @RequestParam(value = "postID", required = false, defaultValue = "0") long postID, @RequestParam(value = "commentID", required = false, defaultValue = "0") long commentID) {
        if (postID != 0)
            return new ResponseEntity<>(commentService.pageNumberListByPost(page, postID), HttpStatus.OK);
        else return new ResponseEntity<>(commentService.pageNumberListByComment(page, commentID), HttpStatus.OK);
    }
}
