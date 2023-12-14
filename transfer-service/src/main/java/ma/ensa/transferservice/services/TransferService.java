package ma.ensa.transferservice.services;


import ma.ensa.transferservice.dto.*;

import java.util.List;


public interface TransferService {

    TransferResponseDto getTransfer(long ref);

    List<Long> emitTransfer(SendDto dto);

    String serveTransfer(TransferDto dto);

    String revertTransfer(TransferDto dto);

    CancelResponseDto cancelTransfer(TransferDto dto);

    String blockTransfer(TransferDto dto);

    String unblockTransfer(TransferDto dto);

    List<TransferResponseDto> getAllTransfers(SearchFilter filter);

    TransferResume getTransferResume(long senderId, int lastDays);
}