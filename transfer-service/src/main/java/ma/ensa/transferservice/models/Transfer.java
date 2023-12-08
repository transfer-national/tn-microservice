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
public class Transfer implements Cloneable{


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

    private long groupId;

    @OneToMany(mappedBy = "transfer")
    private List<TransferStatusDetails> statuses;

    @PrePersist
    public void init(){

        var random = new Random();

        this.ref = random.nextLong(
            1_000_000_000_000L,
            10_000_000_000_000L
        );
    }

    public TransferStatusDetails getSendingDetails(){
        return statuses.stream()
                .findFirst()
                .orElseThrow();
    }

    public TransferStatusDetails getStatusDetails(){
        // find the last status
        return statuses.stream()
                .reduce((f,s) -> s)
                .orElseThrow(); // unexpected exception
    }

    public LocalDateTime getSentAt(){
        return getSendingDetails()
            .getUpdatedAt();
    }

    public boolean isMultiple(){
        return groupId != 0L;
    }

    @Override
    public Transfer clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Transfer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}