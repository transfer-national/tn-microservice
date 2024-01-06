package ma.ensa.clientservice.repositories;

import ma.ensa.clientservice.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByIdNumber(String idNumber);

}
