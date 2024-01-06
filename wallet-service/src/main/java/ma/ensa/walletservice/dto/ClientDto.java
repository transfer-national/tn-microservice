package ma.ensa.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ClientDto {

    private String firstName;

    private String lastName;

    private String idNumber;

    private String gsm;

    private String email;

}
