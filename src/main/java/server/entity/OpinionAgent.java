package server.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "opinions")
public class OpinionAgent {

    public OpinionAgent(UserEntity user, PostEntity post, boolean like) {
        this.user = user;
        this.post = post;
        this.positive = like;
        this.rating = user.getReputation();
    }

    public OpinionAgent(UserEntity user, CommentEntity comment, boolean like) {
        this.user = user;
        this.comment = comment;
        this.positive = like;
        this.rating = user.getReputation();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opinion_id")
    private long opinionID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @Column
    private boolean positive;

    @Column
    private long rating;

}
