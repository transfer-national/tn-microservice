package ma.ensa.transferservice.repositories;

import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.TransferStatusDetails;
import ma.ensa.transferservice.models.enums.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TsdRepository extends JpaRepository<TransferStatusDetails, Long> {

    List<TransferStatus> findAllByTransfer(Transfer transfer);


    TransferStatus findByTransferOrderByIdDesc(Transfer transfer);

}
