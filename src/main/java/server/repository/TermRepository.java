package server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.ServerEntity;
import server.entity.TermEntity;

import java.util.List;

public interface TermRepository extends JpaRepository<TermEntity, Long> {
    long countAllByTermIDIsGreaterThan(long i);

    TermEntity findFirstByTerm(String termName);

    boolean existsByTerm(String termName);

    Page<TermEntity> findAllByTermServerAndStatusOrStatus(ServerEntity serverEntity, String status, String status1, Pageable pageable);

    long countAllByTermServer(ServerEntity serverEntity);

    List<TermEntity> findAllByStatus(String status);
}
