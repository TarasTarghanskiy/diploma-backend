package server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "votes")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private long voteID;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date voteCreateDate = new Date();

    @Column
    private long voteMinReputation;

    @Column
    private boolean isVoteActive;

    @OneToMany(mappedBy = "vote", fetch = FetchType.EAGER)
    private List<VoteVariantEntity> voteVariantList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_server_id")
    private ServerEntity voteServer;

    @Column(length = 20, nullable = false)
    private String voteType;

    @Column
    private long subjectID;

    @Column
    private String description;

    @ManyToMany
    @JoinTable(
            name = "user_vote",
            joinColumns = { @JoinColumn(name = "vote_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<UserEntity> userSet = new HashSet<>();
}
