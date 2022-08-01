package ru.pnz.floridov.RestDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pnz.floridov.RestDemo.model.DebetAccount;

import java.math.BigDecimal;

@Repository
public interface DebetAccountRepository extends JpaRepository<DebetAccount,Long> {

    @Query(value = "select sum (amount) from debet_account where client_id=:id and currency=:currency", nativeQuery = true)
    BigDecimal findAllDebetBalanceDetailsById(Long id, String currency);
}
