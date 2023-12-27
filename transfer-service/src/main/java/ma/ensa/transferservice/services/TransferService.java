package ma.ensa.transferservice.services;


import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.dto.sms.PinTx;
import ma.ensa.transferservice.dto.tx.SendDto;
import ma.ensa.transferservice.dto.tx.TransferDto;
import ma.ensa.transferservice.dto.tx.TransferResponseDto;

import java.util.List;


public interface TransferService {

    TransferResponseDto getTransfer(long ref);

    List<PinTx> emitTransfer(SendDto dto);

    String serveTransfer(TransferDto dto);

    String revertTransfer(TransferDto dto);

    CancelResponseDto cancelTransfer(TransferDto dto);

    String blockTransfer(TransferDto dto);

    String unblockTransfer(TransferDto dto);

    List<TransferResponseDto> getAllTransfers(SearchFilter filter);

    TransferResume getTransferResume(long senderId, int lastDays);
}