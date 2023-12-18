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

    private TransferType transferType;

    private List<UnitTransfer> unitTransfers;

    public int getTxCount(){
        return unitTransfers.size();
    }

    {
        setActionType(ActionType.EMIT);
    }

    @Data
    public static class UnitTransfer{
        private Long recipientId;

        private double amount;

        private FeeType feeType;

        private boolean notificationEnabled;
    }

}