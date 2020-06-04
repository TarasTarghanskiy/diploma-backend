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
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long accountID;

    @Column(name = "account_name", unique = true, nullable = false, length = 10)
    private String accountName;

    @Column(name = "account_password", nullable = false)
    private String accountPassword;

    @Column(nullable = false)
    private boolean enable;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @OneToMany(mappedBy = "serverAccount", fetch = FetchType.LAZY)
    private List<ServerEntity> serverAccountList = new ArrayList<>();

    @Lob
    @Column(name = "account_image", columnDefinition = "BLOB")
    private byte[] accountAvatar;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private List<UserEntity> accountUserList = new ArrayList<>();

//    @ManyToMany(mappedBy = "storyAccountList")
//    Set<StoryEntity> accountStoryList = new HashSet<>();

    @OneToMany(mappedBy = "story")
    private List<StoryAccountEntity> storySet = new ArrayList<>();
}
