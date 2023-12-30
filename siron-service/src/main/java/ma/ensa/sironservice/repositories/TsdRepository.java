package ma.ensa.sironservice.repositories;

import ma.ensa.sironservice.models.TransferStatusDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TsdRepository
        extends JpaRepository<TransferStatusDetails, Long> {}
