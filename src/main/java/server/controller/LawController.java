package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.DTO.LawDTO;
import server.DTO.PostDTO;
import server.DTO.TermDTO;
import server.entity.LawEntity;
import server.repository.LawRepository;
import server.service.LawService;
import server.service.PostService;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/law")
@CrossOrigin
public class LawController {

    @Autowired
    private LawService lawService;

    @PostMapping("/create")
    public ResponseEntity<List<String>> create(@RequestBody LawDTO lawDTO) throws IOException {
        return new ResponseEntity<>(lawService.create(lawDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<List<LawDTO>> getList(@PathVariable int page, @PathParam("serverID") long serverID) {
        return new ResponseEntity<>(lawService.findAllByList(page, serverID), HttpStatus.OK);
    }

    @GetMapping("/pages/{page}")
    public ResponseEntity<List<Integer>> getPageCount(@PathVariable int page, @PathParam("serverID") long serverID) {
        return new ResponseEntity<>(lawService.pageNumberList(page, serverID), HttpStatus.OK);
    }
}
