package server.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "stories")
public class StoryEntity implements Comparable<StoryEntity>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    private long storyID;

    @Column(name = "story_text", nullable = false)
    private String text;

    @Column(length = 50, nullable = false)
    private String link;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

//    @ManyToMany
//    @JoinTable(
//            name = "story_account",
//            joinColumns = @JoinColumn(name = "account_id"),
//            inverseJoinColumns = @JoinColumn(name = "story_id"))
//    Set<AccountEntity> storyAccountList = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private List<StoryAccountEntity> accountSet = new ArrayList<>();

    @Override
    public int compareTo(StoryEntity o) {
        return Math.toIntExact(o.storyID - storyID);
    }
}
