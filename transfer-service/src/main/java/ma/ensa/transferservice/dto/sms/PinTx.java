package ma.ensa.transferservice.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PinTx {

    private String phoneNumber;

    private Long txRef;

    private String pinCode;

}
