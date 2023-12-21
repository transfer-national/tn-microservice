package ma.ensa.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class AuthRequest {

    private String userId;

    private String password;

}
