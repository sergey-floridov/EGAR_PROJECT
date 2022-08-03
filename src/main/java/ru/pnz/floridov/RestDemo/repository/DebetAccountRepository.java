package ru.pnz.floridov.RestDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pnz.floridov.RestDemo.model.DebetAccount;

import java.math.BigDecimal;

@Repository
public interface DebetAccountRepository extends JpaRepository<DebetAccount,Long> {

    @Query("SELECT sum(amount) from DebetAccount where client.id=:id and currency=:currency")
    BigDecimal findAllDebetBalanceDetailsById(Long id, String currency);



}
