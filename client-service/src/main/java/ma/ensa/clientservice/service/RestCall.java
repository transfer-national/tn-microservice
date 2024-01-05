package ma.ensa.clientservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestCall {

    private final RestTemplate restTemplate;

    public boolean hasTheWallet(Long ref){

        var result = restTemplate.getForObject(
                "lb://wallet-service/wallet/exists/{ref}",
                Boolean.class, ref
        );

        return Optional.ofNullable(result)
                .orElse(false);

    }

}
