package ru.pnz.floridov.RestDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pnz.floridov.RestDemo.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
   Client findClientByLastName(String lastName);

}
