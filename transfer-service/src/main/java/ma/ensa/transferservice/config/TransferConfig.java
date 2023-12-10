package ma.ensa.transferservice.config;

import lombok.Data;
import ma.ensa.transferservice.models.enums.FeeType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Configuration
@ConfigurationProperties(prefix = "transfer")
@Data
public class TransferConfig {
    private double fee;
    private int daysOfValidity;
    private int daysOfClaiming;

    public double getFeeForSender(FeeType feeType){
        return switch (feeType){
            case SENDER -> fee;
            case FIFTY_FIFTY -> fee/2;
            default -> 0;
        };
    }

    public double getFeeForRecipient(FeeType feeType){
        return switch (feeType){
            case RECIPIENT -> fee;
            case FIFTY_FIFTY -> fee/2;
            default -> 0;
        };
    }

    public boolean isValid(LocalDateTime sentAt){
        return sentAt
                .plusDays(daysOfValidity)
                .isAfter(now());
    }

    public boolean isClaimable(LocalDateTime sentAt){
        return sentAt
                .plusDays(daysOfClaiming)
                .isAfter(now());
    }
}
