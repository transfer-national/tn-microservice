package ma.ensa.sironservice.repositories;

import ma.ensa.sironservice.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
