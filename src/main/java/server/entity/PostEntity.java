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
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long postID;

    @Column
    private String title;


    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(columnDefinition="TEXT")
    private String text;

    @Column()
    private long viewCount;

    @Column
    private String status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_user_id")
    private UserEntity postUser;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_server_id")
    private ServerEntity postServer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commentPost")
    private List<CommentEntity> postCommentList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<OpinionAgent> opinionAgentList = new ArrayList<>();

}
