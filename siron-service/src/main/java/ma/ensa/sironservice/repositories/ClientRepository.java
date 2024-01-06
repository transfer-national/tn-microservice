package ma.ensa.sironservice.repositories;

import ma.ensa.sironservice.models.BlackListedClient;
import ma.ensa.sironservice.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository
        extends JpaRepository<Client, Long> {
}
