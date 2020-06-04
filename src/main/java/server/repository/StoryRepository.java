package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.StoryEntity;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {
//    Long countAllByCreationDateExists();
    Long countAllByStoryIDIsGreaterThan(long storyID);

}
