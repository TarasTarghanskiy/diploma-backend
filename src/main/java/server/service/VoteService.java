package server.service;

import server.DTO.VoteDTO;
import server.DiplomaProject;

import java.util.List;

public interface VoteService {
    void pastDay();

    List<VoteDTO> getVotePageList(int page, long serverID, String accountName);

    List<Integer> getPageCount(int page, long serverID);

    void createVoteByLaw(long serverID, String text, long subjectID, boolean delete);

    void createVoteByTerm(long serverID, String text, long subjectID, String type);

    void vote(long serverID, long voteID, long variantID, String accountName);
}
