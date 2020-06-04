package server.service;

import server.DTO.ServerDTO;
import server.DTO.UserDTO;
import server.entity.ServerEntity;

import java.util.List;

public interface ServerService {

    List<ServerDTO> getServerList(int page);

    List<Integer> getPageCount(int page);

    List<String> create(ServerDTO serverDTO, String accountName);

    List<String> createUser(UserDTO userDTO, String accountName);

    UserDTO getUserDTO(long serverID, String accountName);

    long getWholeReputationCount();
}
