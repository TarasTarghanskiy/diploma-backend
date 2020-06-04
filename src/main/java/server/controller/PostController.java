package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.DTO.PostDTO;
import server.entity.PostEntity;
import server.entity.ServerEntity;
import server.entity.UserEntity;
import server.repository.PostRepository;
import server.repository.ServerRepository;
import server.repository.UserRepository;
import server.service.PostService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServerRepository serverRepository;

    @PostMapping("/create")
    public ResponseEntity<List<String>> createAccount(@RequestBody PostDTO postDTO) throws IOException {
        postService.save(postDTO,  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<List<PostDTO>> getHistoryList(@PathVariable int page, @RequestParam("serverID") long serverID) {
        return new ResponseEntity<>(postService.findAllByList(page, serverID), HttpStatus.OK);
    }

    @GetMapping("/{postID}")
    public ResponseEntity<PostDTO> getHistoryList(@PathVariable long postID) {
        return new ResponseEntity<>(postService.getPostByPostID(postID), HttpStatus.OK);
    }

    @GetMapping("/pages/{page}")
    public ResponseEntity<List<Integer>> getHistoryPageCount(@PathVariable int page, @RequestParam("serverID") long serverID) {
        return new ResponseEntity<>(postService.pageNumberList(page,serverID), HttpStatus.OK);
    }
}
