package ma.ensa.transferservice.repositories;

import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.enums.TransferStatusDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TsdRepository extends JpaRepository<ma.ensa.transferservice.models.TransferStatusDetail, Long> {

    List<TransferStatusDetail> findAllByTransfer(Transfer transfer);


    TransferStatusDetail findByTransferOrderByIdDesc(Transfer transfer);

}
