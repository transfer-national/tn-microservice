package ma.ensa.transferservice.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.ensa.transferservice.dto.request.TransferDto;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ServeDto extends TransferDto {

    private boolean toWallet;

}
