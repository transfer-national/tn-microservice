package ma.ensa.transferservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.transferservice.config.TransferConfig;
import ma.ensa.transferservice.models.enums.FeeType;
import ma.ensa.transferservice.models.enums.TransferStatus;
import ma.ensa.transferservice.models.enums.TransferType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;
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

    private boolean notificationEnabled;

    @OneToMany(mappedBy = "transfer")
    private List<TransferStatusHistory> statusHistories;

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

    public LocalDateTime getSentAt(){

        return statusHistories
                .stream()
                .filter(s -> s.getStatus() == TransferStatus.TO_SERVE)
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getUpdatedAt();
    }

    public boolean isValid(){
        return getSentAt()
                .plusDays(transferConfig.getDaysOfValidity())
                .isAfter(now());
    }

    public boolean isClaimable(){
        return getSentAt()
                .plusDays(transferConfig.getDaysOfClaiming())
                .isAfter(now());
    }

}