package server.service;

import server.DTO.LawDTO;
import server.DTO.TermDTO;

import java.util.List;

public interface TermService {
    List<String> create(TermDTO dto);

    List<TermDTO> findAllByList(int page, long serverID);

    List<Integer> pageNumberList(int page, long serverID);

    TermDTO getPostByPostID(long postID);

    List<String> checkBlockedWords(String text);
}
