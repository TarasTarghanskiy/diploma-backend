package server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DTO.LawDTO;
import server.DTO.ServerDTO;
import server.DTO.TermDTO;
import server.entity.TermEntity;
import server.repository.TermRepository;
import server.service.LawService;
import server.service.TermService;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/term")
@CrossOrigin
public class TermController {
    @Autowired
    private TermService termService;

    @Autowired
    private TermRepository termRepository;

    @PostMapping("/create")
    public ResponseEntity<List<String>> create(@RequestBody TermDTO termDTO) throws IOException {
        return new ResponseEntity<>(termService.create(termDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<List<TermDTO>> getList(@PathVariable int page, @PathParam("serverID") long serverID) {
        return new ResponseEntity<>(termService.findAllByList(page, serverID), HttpStatus.OK);
    }

    @GetMapping("/pages/{page}")
    public ResponseEntity<List<Integer>> getPageCount(@PathVariable int page, @PathParam("serverID") long serverID) {
        return new ResponseEntity<>(termService.pageNumberList(page, serverID), HttpStatus.OK);
    }

    @GetMapping("/{postID}")
    public ResponseEntity<TermDTO> getHistoryList(@PathVariable long postID) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        return new ResponseEntity<>(termService.getPostByPostID(postID), HttpStatus.OK);
    }
}
