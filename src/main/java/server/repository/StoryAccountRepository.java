package server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.AccountEntity;
import server.entity.StoryAccountEntity;
import server.entity.StoryAccountAgentPK;

import java.util.List;

@Repository
public interface StoryAccountRepository extends JpaRepository<StoryAccountEntity, StoryAccountAgentPK> {
    List<StoryAccountEntity> findAllByAccount(AccountEntity accountEntity);
    Page<StoryAccountEntity> findAllByAccount(AccountEntity accountEntity, Pageable pageable);
    Integer countAllByAccountAndViewed(AccountEntity accountEntity, boolean viewed);
}
