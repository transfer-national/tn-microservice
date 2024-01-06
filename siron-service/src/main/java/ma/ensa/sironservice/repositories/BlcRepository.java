package ma.ensa.sironservice.repositories;

import ma.ensa.sironservice.models.BlackListedClient;
import ma.ensa.sironservice.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlcRepository
        extends JpaRepository<BlackListedClient, Long> {

    Optional<BlackListedClient>
            findBlackListedClientByClient(Client client);

}
