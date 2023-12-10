package ma.ensa.transferservice.dto.request;


import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.ensa.transferservice.models.enums.FeeType;
import ma.ensa.transferservice.models.enums.TransferType;

import java.util.List;



@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SendDto extends TransferDto {

    private Long senderRef;

    private List<Long> recipientIds;

    private double amount;

    private TransferType transferType;

    private FeeType feeType;

    private boolean isNotificationEnabled;

    public int getRecipientCount(){
        return recipientIds.size();
    }

}
