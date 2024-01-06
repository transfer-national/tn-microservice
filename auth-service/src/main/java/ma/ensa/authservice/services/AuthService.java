package ma.ensa.authservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.authservice.dto.AuthRequest;
import ma.ensa.authservice.dto.AuthResponse;
import ma.ensa.authservice.models.user.User;
import ma.ensa.authservice.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String setPassword(AuthRequest dto){

        // find the user by id
        var user =  (User) userService
            .loadUserByUsername(dto.getUserId());

        // set the password
        user.setPassword(
            passwordEncoder.encode(dto.getPassword())
        );

        // save the change into the database
        userRepository.save(user);

        // return the success message
        return String.format(
                "the password of %s has been updated successfully",
                dto.getUserId()
        );
    }


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
                .userId(userId)
                .role(user.getRole())
                .build();
    }
}
