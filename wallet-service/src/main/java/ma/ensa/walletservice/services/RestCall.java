package ma.ensa.walletservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensa.walletservice.dto.AuthRequest;
import ma.ensa.walletservice.dto.ClientDto;
import ma.ensa.walletservice.exceptions.ClientNotFoundException;
import ma.ensa.walletservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.HttpStatusCode.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestCall {

    private final RestTemplate restTemplate;

    public ClientDto getClient(String clientRef) {

        var clientEntity  = restTemplate.getForEntity(
                "lb://client-service/client/cin/{ref}",
                ClientDto.class, clientRef
        );

        log.info("there we gooooo");

        return Optional.of(clientEntity)
                .filter(e -> e.getStatusCode() == valueOf(200))
                .map(HttpEntity::getBody)
                .orElseThrow(
                        () -> new ClientNotFoundException(clientRef)
                );
    }

    public void setPassword(String _userId, String _password){

        // construct the body
        var body = AuthRequest.builder()
                .userId(_userId)
                .password(_password)
                .build();

        // call the auth service to set the password
        var response = restTemplate.exchange(
                "lb://auth-service/auth/password",
                HttpMethod.PUT,
                new HttpEntity<>(body),
                String.class
        );

        Optional.of(response)
                .filter(e -> e.getStatusCode() == valueOf(200))
                .map(r -> {
                    log.info(r.getBody());
                    return r;
                })
                .orElseThrow(
                    () -> new UserNotFoundException(_userId)
                );
    }

}
