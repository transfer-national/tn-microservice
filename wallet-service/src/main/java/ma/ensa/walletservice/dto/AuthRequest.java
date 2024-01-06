package ma.ensa.walletservice.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AuthRequest {

    private String userId;
    private String password;

}
