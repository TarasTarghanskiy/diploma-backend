package server.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.entity.StoryAccountAgentPK;
import server.entity.StoryEntity;

@Getter
@Setter
@NoArgsConstructor
public class StoryAccountDTO {
    private StoryAccountAgentPK id;

    private StoryDTO story;

    private boolean viewed;
}
