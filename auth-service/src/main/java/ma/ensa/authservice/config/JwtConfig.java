package ma.ensa.authservice.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

import static java.time.ZoneId.systemDefault;


@Configuration
@ConfigurationProperties("jwt")
public class JwtConfig {


    private String secretKey;

    private Long dayOfValidity;


    public Date getExpirationDate() {

        return Date.from(LocalDateTime.now()
                .plusDays(dayOfValidity)
                .atZone(systemDefault())
                .toInstant()
        );

    }

}