package server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.LawEntity;
import server.entity.ServerEntity;

public interface LawRepository extends JpaRepository<LawEntity, Long> {
    long countAllByLawIDIsGreaterThan(long i);

    long countAllByLawServer(ServerEntity serverEntity);

    boolean existsLawEntityByLawText(String text);

    Page<LawEntity> findAllByStatusAndLawServer(String status, ServerEntity serverEntity, Pageable pageable);
}
