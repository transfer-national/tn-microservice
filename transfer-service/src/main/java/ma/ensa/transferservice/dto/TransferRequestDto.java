package ma.ensa.transferservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.transferservice.models.enums.FeeType;
import ma.ensa.transferservice.models.enums.TransferType;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequestDto {

    private Long senderRef;

    private Long recipientId;

    private String sentById;

    private double amount;

    private TransferType transferType;

    private FeeType feeType;

    private String reason;

    private boolean isNotificationEnabled;
}
