package ma.ensa.sironservice.repositories;

import ma.ensa.sironservice.models.TransferStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TshRepository
        extends JpaRepository<TransferStatusHistory, Long> {}
