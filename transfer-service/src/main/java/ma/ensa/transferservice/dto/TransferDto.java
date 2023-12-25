package ma.ensa.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransferDto {

    // main attributes
    private long ref;

    private String reason;

    private String userId;

    private ActionType actionType;

    // for serving
    private boolean toWallet;

}
