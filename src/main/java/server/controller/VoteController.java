package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.DTO.VoteDTO;
import server.service.VoteService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/vote")
@CrossOrigin
public class VoteController {
    @Autowired
    VoteService voteService;

    @GetMapping("/list/{page}")
    public ResponseEntity<List<VoteDTO>> getList(@PathVariable int page, @PathParam("serverID") long serverID) {
        return new ResponseEntity<>(voteService.getVotePageList(page, serverID, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()), HttpStatus.OK);
    }

    @GetMapping("/pages/{page}")
    public ResponseEntity<List<Integer>> getHistoryPageCount(@PathVariable int page, @PathParam("serverID") long serverID) {
        return new ResponseEntity<>(voteService.getPageCount(page, serverID), HttpStatus.OK);
    }

    @GetMapping("/{serverID}")
    public void setVote(@PathVariable(name = "serverID") long serverID, @PathParam("voteID") long voteID, @PathParam("variantID") long variantID) {
        voteService.vote(serverID, voteID, variantID, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
