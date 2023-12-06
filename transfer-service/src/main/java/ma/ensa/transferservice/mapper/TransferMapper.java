package ma.ensa.transferservice.mapper;

import ma.ensa.transferservice.dto.TransferRequestDto;
import ma.ensa.transferservice.dto.TransferResponseDto;
import ma.ensa.transferservice.models.Client;
import ma.ensa.transferservice.models.Recipient;
import ma.ensa.transferservice.models.Transfer;

public class TransferMapper {

    public static Transfer toEntity(TransferRequestDto dto){

        var sender = new Client(dto.getSenderRef());
        var recipient = new Recipient(dto.getRecipientId());

        return Transfer.builder()
                .sender(sender)
                .recipient(recipient)
                .amount(dto.getAmount())
                .feeType(dto.getFeeType())
                .transferType(dto.getTransferType())
                .notificationEnabled(dto.isNotificationEnabled())
                .build();
    }


    public static TransferResponseDto toDto(Transfer transfer) {

        return TransferResponseDto.builder()
                .ref(transfer.getRef())
                .amount(transfer.getAmount())
                .statuses(transfer.getStatusHistories())
                .build();

    }
}
