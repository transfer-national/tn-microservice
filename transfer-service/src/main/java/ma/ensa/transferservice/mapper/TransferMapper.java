package ma.ensa.transferservice.mapper;

import lombok.RequiredArgsConstructor;
import ma.ensa.transferservice.dto.tx.SendDto;
import ma.ensa.transferservice.dto.tx.TransferResponseDto;
import ma.ensa.transferservice.models.Client;
import ma.ensa.transferservice.models.Recipient;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.services.RestCall;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TransferMapper {

    private final RestCall rest;

    public List<Transfer> toEntity(SendDto dto){

        var sender = new Client(dto.getSenderRef());

        // generate long id
        long groupId = (dto.getTxCount() != 1) ?
                generateLong() : 0L;

        var unitTransferBuilder = Transfer.builder()
                .sender(sender)
                .transferType(dto.getTransferType())
                .groupId(groupId);

        return dto.getUnitTransfers()
            .stream()
            .map(ut -> unitTransferBuilder
                .recipient(new Recipient(ut.getRecipientId()))
                .amount(ut.getAmount())
                .notificationEnabled(ut.isNotificationEnabled())
                .feeType(ut.getFeeType())
                .reason(ut.getReason())
                .build()
            )
            .toList();
    }


    public TransferResponseDto toDto(Transfer t) {
        return TransferResponseDto.builder()
            .ref(t.getRef())
            .amount(t.getAmount())
            .statuses(t.getStatuses())
            .sender(
                rest.getSender(t.getSender())
            )
            .recipient(
                rest.getRecipient(t.getRecipient())
            )
            .build();
    }

    private long generateLong(){
        var random = new Random();
        return random.nextLong(
            1_000_000L,
            1_000_000_000L
        );
    }
}
