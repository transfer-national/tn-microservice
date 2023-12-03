package ma.ensa.transferservice.dto;


import lombok.Data;
import ma.ensa.transferservice.models.enums.FeeType;
import ma.ensa.transferservice.models.enums.TransferType;


@Data
public class TransferDto {

    private Long senderRef;

    private Long recipientId;

    private String sentById;

    private double amount;

    private TransferType transferType;

    private FeeType feeType;

    private String reason;

    private boolean isNotificationEnabled;
}
