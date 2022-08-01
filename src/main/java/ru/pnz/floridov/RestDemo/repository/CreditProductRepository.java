package ru.pnz.floridov.RestDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pnz.floridov.RestDemo.model.CreditProduct;

import java.math.BigDecimal;

@Repository
public interface CreditProductRepository extends JpaRepository<CreditProduct,Long> {


    @Query(value = "select sum (amount) from credit_product cp " +
            "join client c on c.id = cp.client_id " +
            "where c.id=:id and currency=:currency",
            nativeQuery = true)
    BigDecimal findAllCreditBalanceDetailsById(Long id, String currency);

}



