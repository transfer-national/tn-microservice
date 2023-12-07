package ma.ensa.authservice.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

import static java.time.ZoneId.systemDefault;


@Configuration
@ConfigurationProperties("jwt")
@Data
public class JwtConfig {


    private String secretKey;

    private Long daysOfValidity;


    public Date getExpirationDate() {

        return Date.from(LocalDateTime.now()
                .plusDays(daysOfValidity)
                .atZone(systemDefault())
                .toInstant()
        );

    }

}