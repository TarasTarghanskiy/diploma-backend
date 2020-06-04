package server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "story_account_agent")

@Getter
@Setter
@NoArgsConstructor
public class StoryAccountEntity {
    public StoryAccountEntity(StoryEntity story, AccountEntity account) {
        this.id = new StoryAccountAgentPK(story.getStoryID(), account.getAccountID());
        this.story = story;
        this.account = account;
    }

    @EmbeddedId
    private StoryAccountAgentPK id;

    @ManyToOne
    @MapsId("storyID")
    @JoinColumn(name = "story_id")
    private StoryEntity story;

    @ManyToOne
    @MapsId("accountID")
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Column
    private boolean viewed;
}
