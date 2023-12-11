package ma.ensa.clientservice.repositories;

import ma.ensa.clientservice.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecipientRepository extends JpaRepository<Recipient,Long> {

    List<Recipient> findAllByClient_Ref(Long clientRef);


}
