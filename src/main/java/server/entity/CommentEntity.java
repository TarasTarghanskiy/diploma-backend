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
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long commentID;

    @Column(columnDefinition="TEXT")
    private String text;


    @Column(name = "is_deleting")
    private boolean delete;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_post_id")
    private PostEntity commentPost;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_user_id")
    private UserEntity commentUser;

    @OneToMany(mappedBy = "ownerComment", fetch = FetchType.LAZY)
    private List<CommentEntity> childCommentList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_comment_id")
    private CommentEntity ownerComment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    private List<OpinionAgent> opinionAgentList = new ArrayList<>();
}
