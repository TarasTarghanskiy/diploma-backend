package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.AccountEntity;
import server.entity.ServerEntity;
import server.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByUserServerAndUserAccount(ServerEntity serverEntity, AccountEntity accountEntity);
}
