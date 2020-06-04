package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.ServerEntity;

@Repository
public interface ServerRepository extends JpaRepository<ServerEntity, Long> {
    Long countAllByServerIDIsGreaterThan(long serverID);
}
