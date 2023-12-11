package ma.ensa.clientservice.repositories;

import ma.ensa.clientservice.entities.Client;
import ma.ensa.clientservice.entities.Receipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceipientRepository extends JpaRepository<Receipient,Long> {


}
