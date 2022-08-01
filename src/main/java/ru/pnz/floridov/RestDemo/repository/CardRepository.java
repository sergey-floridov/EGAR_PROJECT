package ru.pnz.floridov.RestDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pnz.floridov.RestDemo.model.Card;


@Repository
public interface CardRepository extends JpaRepository<Card,Long> {
}
