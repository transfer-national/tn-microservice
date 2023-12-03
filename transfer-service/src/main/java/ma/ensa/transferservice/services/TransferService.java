package ma.ensa.transferservice.services;


import ma.ensa.transferservice.dto.TransferDto;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.enums.TransferStatus;
import org.springframework.stereotype.Service;

@Service
public interface TransferService {

    long emitTransfer(TransferDto transfer);

    TransferStatus getTransferStatus(long ref);

    void serveTransfer(long ref);

    void revertTransfer(long ref);

    void cancelTransfer(long ref);

    void blockTransfer(long ref);

    void unblockTransfer();

}