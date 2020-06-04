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
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userID;

    @Column(name = "user_name", unique = true, nullable = false, length = 15)
    private String userName;

    @Column
    private long reputation;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @OneToMany(mappedBy = "postUser", fetch = FetchType.LAZY)
    private List<PostEntity> userPostList = new ArrayList<>();

    @OneToMany(mappedBy = "commentUser", fetch = FetchType.LAZY)
    private List<CommentEntity> userCommentList = new ArrayList<>();

//    @OneToMany(mappedBy = "judgeUser", fetch = FetchType.LAZY)
//    private List<TrialEntity> userTrialJudgeList = new ArrayList<>();
//
//    @ManyToMany(mappedBy = "protectionSideUserList", fetch = FetchType.LAZY)
//    private List<TrialEntity> userTrialProtectionList = new ArrayList<>();
//
//    @ManyToMany(mappedBy = "prosecutionSideUserList", fetch = FetchType.LAZY)
//    private List<TrialEntity> userTrialProsecutionList = new ArrayList<>();

    @OneToMany(mappedBy = "trial")
    private Set<UserTrialAgent> trialSet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private AccountEntity userAccount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_server_id")
    private ServerEntity userServer;

    @ManyToMany(mappedBy = "userSet")
    private Set<VoteEntity> voteSet;

    @Column(length = 30, nullable = false)
    private String status;
}
