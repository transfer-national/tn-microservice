package ma.ensa.transferservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.transferservice.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface TransferRepository extends JpaRepository<Transfer, Long> {


}
