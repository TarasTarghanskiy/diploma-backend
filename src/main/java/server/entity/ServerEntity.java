package server.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "servers")
public class ServerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_id")
    private long serverID;

    @Column
    private String name;

    @Column
    private String description;



    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column
    private long viewsCount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "server_account_id")
    private AccountEntity serverAccount;

    @OneToMany(mappedBy = "userServer", fetch = FetchType.LAZY)
    private List<UserEntity> userServerList = new ArrayList<>();

    @OneToMany(mappedBy = "lawServer", fetch = FetchType.LAZY)
    private List<LawEntity> lawServerList = new ArrayList<>();

    @OneToMany(mappedBy = "termServer", fetch = FetchType.LAZY)
    private List<TermEntity> termServerList = new ArrayList<>();

    @OneToMany(mappedBy = "trialServer", fetch = FetchType.LAZY)
    private List<TrialEntity> trialServerList = new ArrayList<>();

    @OneToMany(mappedBy = "postServer", fetch = FetchType.LAZY)
    private List<PostEntity> postServerList = new ArrayList<>();

    @OneToMany(mappedBy = "voteServer", fetch = FetchType.LAZY)
    private List<VoteEntity> voteServerList = new ArrayList<>();
}
