package server.service;

import server.DTO.LawDTO;

import java.util.List;

public interface LawService {
    List<String> create(LawDTO lawDTO);

    List<LawDTO> findAllByList(int page, long serverID);

    List<Integer> pageNumberList(int page, long serverID);
}
