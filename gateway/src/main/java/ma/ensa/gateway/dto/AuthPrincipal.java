package ma.ensa.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthPrincipal implements Principal {

    private String userId;

    private String token;

    private Role role;

    @Override
    public String getName() {
        return userId;
    }
}