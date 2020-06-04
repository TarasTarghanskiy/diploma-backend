package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.VoteVariantEntity;

@Repository
public interface VoteVariantRepository extends JpaRepository<VoteVariantEntity, Long> {
}
