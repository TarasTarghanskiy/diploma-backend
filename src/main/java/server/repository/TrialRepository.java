package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.TrialEntity;

@Repository
public interface TrialRepository extends JpaRepository<TrialEntity, Long> {
}
