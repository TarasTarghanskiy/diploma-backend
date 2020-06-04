package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.UserTrialAgent;
import server.entity.UserTrialAgentPK;

@Repository
public interface UserTrialRepository extends JpaRepository<UserTrialAgent, UserTrialAgentPK> {
}
