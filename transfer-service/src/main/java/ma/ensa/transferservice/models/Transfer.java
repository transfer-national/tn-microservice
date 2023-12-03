package ma.ensa.transferservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.transferservice.config.TransferConfig;
import ma.ensa.transferservice.models.enums.FeeType;
import ma.ensa.transferservice.models.enums.TransferType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Random;

import static java.time.LocalDateTime.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Transfer {

    @Value("transfer")
    @Transient
    private TransferConfig transferConfig;

    @Id
    private long ref;

    @ManyToOne
    private Client sender;

    @ManyToOne
    private Recipient recipient;

    private double amount;

    private TransferType transferType;

    private FeeType feeType;

    @CreationTimestamp
    private LocalDateTime sendAt;

    @ManyToOne
    private Account sentBy;

    private String reason;

    private boolean isNotificationEnabled;

    @PrePersist
    public void init(){

        Random random = new Random();

        this.ref = random.nextLong(
            1_000_000_000_000L,
            10_000_000_000_000L
        );
    }

    public double getFeeForTheSender(){
        double fee = transferConfig.getTransferFee();
        return switch (feeType){
            case SENDER -> fee;
            case FIFTY_FIFTY -> fee/2;
            default -> 0;
        };
    }

    public double getAmountForTheRecipient(){

        double fee = transferConfig.getTransferFee();

        return amount - (fee - getFeeForTheSender());

    }

    public boolean isValid(){
        return sendAt.plusDays(transferConfig.getDaysOfValidity())
              .isAfter(now());
    }

    public boolean isClaimable(){
        return sendAt.plusDays(transferConfig.getDaysOfClaiming())
                .isAfter(now());
    }

}