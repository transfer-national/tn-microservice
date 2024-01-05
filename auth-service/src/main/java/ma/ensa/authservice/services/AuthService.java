package ma.ensa.authservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.authservice.dto.AgentDto;
import ma.ensa.authservice.dto.AuthRequest;
import ma.ensa.authservice.dto.AuthResponse;
import ma.ensa.authservice.models.user.User;
import ma.ensa.authservice.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

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

        var id = dto.getUserId();

        var user = (User) userService.loadUserByUsername(dto.getUserId());

        // authenticate
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getUserId(),
                dto.getPassword()
            )
        );

        if(id.startsWith("b")){
            id = id.replace("b-", "a-");
        }

        var agent = restTemplate.getForObject(
            "lb://agent-service/agent?user={id}",
            AgentDto.class, id
        );

        return AuthResponse.builder()
                .role(user.getRole())
                .token(jwtService.generateToken(user))
                .agent(agent)
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
