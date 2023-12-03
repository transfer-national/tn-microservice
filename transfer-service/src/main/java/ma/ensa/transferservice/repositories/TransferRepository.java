package ma.ensa.transferservice.repositories;

import jakarta.transaction.Transactional;
import ma.ensa.transferservice.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface TransferRepository extends JpaRepository<Transfer, Long> {


}
