package ma.ensa.transferservice.services;


import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.enums.TransferStatusDetail;

import java.util.List;


public interface TransferService {

    Transfer getTransfer(long ref);

    long emitTransfer(TransferRequestDto transfer);


    void serveTransfer(PaymentDto dto);

    void revertTransfer(RevertDto dto);

    void cancelTransfer(PaymentDto dto);

    void blockTransfer(PaymentDto dto);

    void unblockTransfer(PaymentDto dto);

    List<TransferResponseDto> getAllTransfers(SearchFilter filter);
}