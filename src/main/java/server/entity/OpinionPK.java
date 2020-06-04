package server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class OpinionPK implements Serializable {

    public OpinionPK(UserEntity userEntity, PostEntity postEntity) {
        this.userID = userEntity.getUserID();
        this.postID = postEntity.getPostID();
    }

    public OpinionPK(UserEntity userEntity, CommentEntity commentEntity) {
        this.userID = userEntity.getUserID();
        this.commentID = commentEntity.getCommentID();
    }

    @Column(name = "user_id")
    private long userID;

    @Column(name = "post_id")
    private Long postID;

    @Column(name = "comment_id")
    private Long commentID;

}
