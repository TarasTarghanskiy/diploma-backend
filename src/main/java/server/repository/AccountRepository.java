package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity getAccountEntityByAccountName(String accountName);

    boolean existsAccountEntitiesByAccountName(String accountName);

    AccountEntity getAccountEntityByAccountID(long accountID);
}
