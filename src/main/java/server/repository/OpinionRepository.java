package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.*;

import java.util.Set;

@Repository
public interface OpinionRepository extends JpaRepository<OpinionAgent, OpinionPK> {

    OpinionAgent getByCommentAndUser(CommentEntity commentEntity, UserEntity userEntity);

    OpinionAgent getByPostAndUser(PostEntity postEntity, UserEntity userEntity);

    Set<OpinionAgent> findAllByPost(PostEntity postEntity);

    Set<OpinionAgent> findAllByComment(CommentEntity commentEntity);
}
