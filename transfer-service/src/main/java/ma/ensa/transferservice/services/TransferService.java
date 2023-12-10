package ma.ensa.transferservice.services;


import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.dto.request.CancelDto;
import ma.ensa.transferservice.dto.request.RevertDto;
import ma.ensa.transferservice.dto.request.SendDto;
import ma.ensa.transferservice.dto.request.ServeDto;
import ma.ensa.transferservice.models.Transfer;

import java.util.List;


public interface TransferService {

    Transfer getTransfer(long ref);

    List<Long> emitTransfer(SendDto transfer);


    void serveTransfer(ServeDto dto);

    RevertResponseDto revertTransfer(RevertDto dto);

    CancelResponseDto cancelTransfer(CancelDto dto);

    void blockTransfer(ServeDto dto);

    void unblockTransfer(ServeDto dto);

    List<TransferResponseDto> getAllTransfers(SearchFilter filter);
}