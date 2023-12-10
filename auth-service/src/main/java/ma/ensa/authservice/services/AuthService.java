package ma.ensa.authservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.authservice.dto.AuthRequest;
import ma.ensa.authservice.dto.AuthResponse;
import ma.ensa.authservice.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    /**
     * @param dto userId and password
     * @return token
     */
    public AuthResponse login(AuthRequest dto){

        var user = (User) userService.loadUserByUsername(dto.getUserId());

        // authenticate
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getUserId(),
                dto.getPassword()
            )
        );

        return AuthResponse.builder()
                .role(user.getRole())
                .token(jwtService.generateToken(user))
                .build();

    }

    public AuthResponse checkToken(String token){

        // check validity
        jwtService.checkValidity(token);

        var userId = jwtService.extractUsername(token);
        var user = (User) userService.loadUserByUsername(userId);

        // return the userId and the role associated with the token
        return AuthResponse.builder()
                .role(user.getRole())
                .token(token)
                .build();
    }
}
