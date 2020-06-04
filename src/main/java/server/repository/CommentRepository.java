package server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.CommentEntity;
import server.entity.PostEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    long countAllByCommentIDIsGreaterThan(long i);

    Page<CommentEntity> findAllByDeleteAndOwnerComment(boolean delete, CommentEntity commentEntity, Pageable pageable);

    Page<CommentEntity> findAllByDeleteAndCommentPost(boolean delete, PostEntity postEntity, Pageable pageable);

    long countAllByCommentPostAndDelete(PostEntity postEntity, boolean delete);

    long countAllByOwnerCommentAndDelete(CommentEntity commentEntity, boolean delete);
}
