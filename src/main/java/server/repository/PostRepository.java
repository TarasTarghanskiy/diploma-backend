package server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.PostEntity;
import server.entity.ServerEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    long countAllByPostIDIsGreaterThan(long i);

    long countAllByPostServer(ServerEntity serverEntity);

    Page<PostEntity> findAllByPostServer(ServerEntity serverEntity, Pageable pageable);
}
