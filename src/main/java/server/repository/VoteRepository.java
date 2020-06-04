package server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.ServerEntity;
import server.entity.VoteEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    long countAllByVoteIDIsGreaterThan(long i);

    long countAllByVoteServer(ServerEntity serverEntity);

    Page<VoteEntity> findAllByVoteServer(ServerEntity serverEntity, Pageable pageable);

    List<VoteEntity> getAllByVoteCreateDateLessThan(Date date);
}
