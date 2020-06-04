package server.service;

import server.DTO.StoryAccountDTO;
import server.DTO.StoryDTO;
import server.entity.AccountEntity;
import server.entity.ServerEntity;
import server.entity.StoryAccountEntity;

import java.util.List;

public interface StoryService {
    void addStory(String story, String link, ServerEntity serverEntity);

    void addStory(String story, String link, AccountEntity accountEntity);

    List<StoryDTO> getStoryList(int page);

    List<Integer> getPageCount(int page);

    List<StoryAccountDTO> getStoryListByAcc(int page, String accountName);

    Integer getNewsCount(String accountName);
}
