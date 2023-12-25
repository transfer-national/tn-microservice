package ma.ensa.transferservice.repositories;

import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.TransferStatusDetails;
import ma.ensa.transferservice.models.enums.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TSHRepository extends JpaRepository<TransferStatusDetails, Long> {

    List<TransferStatusDetails> findAllByTransfer(Transfer transfer);

    @Query("SELECT t.status FROM TransferStatusDetails t " +
            "WHERE t.transfer.ref = :ref " +
            "ORDER BY t.updatedAt DESC LIMIT 1"
    )
    Optional<TransferStatus> findTransferStatusByRef(
            @Param("ref") long ref
    );

}
