package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DTO.LawDTO;
import server.entity.TrialEntity;
import server.repository.LawRepository;
import server.repository.TrialRepository;

import java.util.List;

@RestController
@RequestMapping("/trial")
@CrossOrigin
public class TrialController {

    @Autowired
    private TrialRepository trialRepository;

    @PostMapping("/report")
    public ResponseEntity<List<String>> createUser(@RequestBody List<LawDTO> lawDTOList,
                                                   @RequestParam(value = "postID", defaultValue = "0", required = false) long postID,
                                                   @RequestParam(value = "commentID", defaultValue = "0", required = false) long commentID) {

        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

}
