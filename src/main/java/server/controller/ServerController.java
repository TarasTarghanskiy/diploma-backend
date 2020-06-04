package server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.DTO.LawDTO;
import server.DTO.ServerDTO;
import server.DTO.UserDTO;
import server.entity.ServerEntity;
import server.repository.ServerRepository;
import server.service.ServerService;
import server.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/server")
@CrossOrigin
public class ServerController {

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    private ServerService serverService;

    @PostMapping("/create")
    public HttpStatus createServer(@RequestBody ServerDTO serverDTO) {
        serverService.create(serverDTO, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return HttpStatus.OK;
    }


    @PostMapping("/create_user")
    public ResponseEntity<List<String>> createUser(@RequestBody UserDTO userDTO) {
        serverService.createUser(userDTO, SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get_user/{serverID}")
    public ResponseEntity<UserDTO> getUserDTO(@PathVariable long serverID) {
        String accountName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (accountName.equals("anonymousUser"))
            return new ResponseEntity<>(null, HttpStatus.OK);
        return new ResponseEntity<>(serverService.getUserDTO(serverID, accountName), HttpStatus.OK);
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<List<ServerDTO>> getHistoryList(@PathVariable int page) {
        return new ResponseEntity<>(serverService.getServerList(page), HttpStatus.OK);
    }

    @GetMapping("/pages/{page}")
    public ResponseEntity<List<Integer>> getHistoryPageCount(@PathVariable int page) {
        return new ResponseEntity<>(serverService.getPageCount(page), HttpStatus.OK);
    }

    @Autowired
    ServerRepository serverRepository;

    @GetMapping("/rd")
    public void getHistoryPageCount() {
        List<ServerEntity> serverEntities = new ArrayList<>();
        ServerEntity serverEntity = new ServerEntity();
        serverEntity.setName("FullLife");
        serverRepository.save(serverEntity);
        serverEntity = new ServerEntity();
        serverEntity.setName("ExtremalCooking");
        serverRepository.save(serverEntity);
        serverEntity = new ServerEntity();
        serverEntity.setName("TestServer");
        serverRepository.save(serverEntity);
        serverEntity = new ServerEntity();
        serverEntity.setName("OnlyScience");
        serverRepository.save(serverEntity);
        serverEntity = new ServerEntity();
        serverEntity.setName("PoliticalHouseExperts");
        serverRepository.save(serverEntity);
        serverEntity = new ServerEntity();
        serverEntity.setName("InterestingThings");
        serverRepository.save(serverEntity);
        serverEntity = new ServerEntity();
        serverEntity.setName("TestServer12");
        serverRepository.save(serverEntity);
        serverEntity = new ServerEntity();
        serverEntity.setName("Fun");
        serverRepository.save(serverEntity);
    }

}
