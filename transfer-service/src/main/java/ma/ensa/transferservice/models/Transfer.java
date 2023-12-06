package ma.ensa.transferservice.models;


import jakarta.persistence.*;
import lombok.*;
import ma.ensa.transferservice.config.TransferConfig;
import ma.ensa.transferservice.models.enums.FeeType;
import ma.ensa.transferservice.models.enums.TransferType;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static java.time.LocalDateTime.now;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Transfer {

    @Setter @Value("${transfer.fee}")
    private static TransferConfig config;


    @Id
    private long ref;

    @ManyToOne
    private Client sender;

    @ManyToOne
    private Recipient recipient;

    private double amount;

    private TransferType transferType;

    private FeeType feeType;

    private boolean notificationEnabled;

    @OneToMany(mappedBy = "transfer")
    private List<TransferStatusDetail> statusHistories;

    @PrePersist
    public void init(){

        var random = new Random();

        this.ref = random.nextLong(
            1_000_000_000_000L,
            10_000_000_000_000L
        );
    }

    public double getFeeForSender(){
        double fee = config.getFee();
        return switch (feeType){
            case SENDER -> fee;
            case FIFTY_FIFTY -> fee/2;
            default -> 0;
        };
    }

    public double getAmountForTheRecipient(){
        return amount - (config.getFee() - getFeeForSender());
    }

    public TransferStatusDetail getSendingDetails(){
        return statusHistories.stream()
                .findFirst()
                .orElseThrow();
    }

    public TransferStatusDetail getStatusDetails(){
        // find the last status
        return statusHistories.stream()
                .reduce((f,s) -> s)
                .orElseThrow();
    }

    public LocalDateTime getSentAt(){
        return getSendingDetails()
            .getUpdatedAt();
    }

    public boolean isValid(){
        return getSentAt()
                .plusDays(config.getDaysOfValidity())
                .isAfter(now());
    }

    public boolean isClaimable(){
        return getSentAt()
                .plusDays(config.getDaysOfClaiming())
                .isAfter(now());
    }

}