package ma.ensa.transferservice.mapper;

import lombok.RequiredArgsConstructor;
import ma.ensa.transferservice.dto.request.SendDto;
import ma.ensa.transferservice.dto.TransferResponseDto;
import ma.ensa.transferservice.models.Client;
import ma.ensa.transferservice.models.Recipient;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.services.impl.RestCall;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TransferMapper {

    private final RestCall rest;


    public List<Transfer> toEntity(SendDto dto){

        var sender = new Client(dto.getSenderRef());

        // generate long id
        long groupId = (dto.getRecipientCount() != 1) ?
                generateLong() : 0L;

        var baseTransfer = Transfer.builder()
            .sender(sender)
            .amount(dto.getAmount())
            .feeType(dto.getFeeType())
            .transferType(dto.getTransferType())
            .notificationEnabled(dto.isNotificationEnabled())
            .groupId(groupId)
            .build();

        return dto.getRecipientIds()
            .stream()
            .map(Recipient::new)
            .map(recipient -> {
                var transfer = baseTransfer.clone();
                transfer.setRecipient(recipient);
                return transfer;
            }).toList();
    }


    public TransferResponseDto toDto(Transfer t) {
        return TransferResponseDto.builder()
            .ref(t.getRef())
            .amount(t.getAmount())
            .statuses(t.getStatuses())
            .sender(
                rest.getSender(t.getSender())
            )
            .sender(
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
