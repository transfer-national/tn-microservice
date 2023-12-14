package ma.ensa.transferservice.dto;

import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.ensa.transferservice.models.Client;
import ma.ensa.transferservice.models.Recipient;
import ma.ensa.transferservice.models.enums.FeeType;
import ma.ensa.transferservice.models.enums.TransferType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SendDto extends TransferDto {

    private long senderRef;

    private List<Long> recipientIds;

    private double amount;

    private TransferType transferType;

    private FeeType feeType;

    private boolean notificationEnabled;

    public int getRecipientCount(){
        return recipientIds.size();
    }

    {
        setActionType(ActionType.EMIT);
    }



}
