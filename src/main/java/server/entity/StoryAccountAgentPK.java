package server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class StoryAccountAgentPK implements Serializable {
    public StoryAccountAgentPK(long storyID, long accountID) {
        this.storyID = storyID;
        this.accountID = accountID;
    }

    @Column(name = "story_id")
    private long storyID;

    @Column(name = "account_id")
    private long accountID;
}
