package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.DTO.StoryAccountDTO;
import server.DTO.StoryDTO;
import server.entity.StoryAccountEntity;
import server.repository.StoryRepository;
import server.service.StoryService;

import java.util.List;

@RestController
@RequestMapping("/story")
@CrossOrigin
public class StoryController {

    private StoryService storyService;

    @Autowired
    private StoryRepository storyRepository;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<List<StoryDTO>> getHistoryList(@PathVariable int page) {
        return new ResponseEntity<>(storyService.getStoryList(page), HttpStatus.OK);
    }

    @GetMapping("/acc_list/{page}")
    public ResponseEntity<List<StoryAccountDTO>> getHistoryListByAcc(@PathVariable int page) {
        return new ResponseEntity<>(storyService.getStoryListByAcc(page, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()), HttpStatus.OK);
    }

    @GetMapping("/pages/{page}")
    public ResponseEntity<List<Integer>> getHistoryPageCount(@PathVariable int page) {
        return new ResponseEntity<>(storyService.getPageCount(page), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getHistoryListByAcc() {
        return new ResponseEntity<>(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), HttpStatus.OK);
    }

    @GetMapping("acc_news")
    public ResponseEntity<Integer> getNewsCount() {
        return new ResponseEntity<>(storyService.getNewsCount(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()), HttpStatus.OK);
    }

}
