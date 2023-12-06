package ma.ensa.transferservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "transfer")
@Data
public class TransferConfig {
    private double fee;
    private int daysOfValidity;
    private int daysOfClaiming;
}
