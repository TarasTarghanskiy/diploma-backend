package server.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "trials")
public class TrialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trial_id")
    private long trialID;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "judge_user_id")
//    private UserEntity judgeUser;

//    @ManyToMany(cascade = { CascadeType.ALL })
//    @JoinTable(
//            name = "trial_user_prosecution",
//            joinColumns = { @JoinColumn(name = "trial_id") },
//            inverseJoinColumns = { @JoinColumn(name = "user_id") }
//    )
//    private List<UserEntity> prosecutionSideUserList;
//
//    @ManyToMany(cascade = { CascadeType.ALL })
//    @JoinTable(
//            name = "trial_user_protection",
//            joinColumns = { @JoinColumn(name = "trial_id") },
//            inverseJoinColumns = { @JoinColumn(name = "user_id") }
//    )
//    private List<UserEntity> protectionSideUserList;


    @OneToMany(mappedBy = "user")
    private Set<UserTrialAgent> userSet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "law_trial_id")
    private LawEntity lawTrial;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "evidence_comment_id")
    private CommentEntity evidenceComment;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "evidence_post_id")
    private PostEntity evidencePost;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate = new Date();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "trial_server_id")
    private ServerEntity trialServer;
}
