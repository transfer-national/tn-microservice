package ma.ensa.transferservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.transferservice.config.TransferConfig;
import ma.ensa.transferservice.models.enums.TransferStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class TransferStatusHistory {

    @Id
    private long id;

    @ManyToOne
    private Transfer transfer;

    @ManyToOne
    private User byUser; // AGENT, WALLET, GAP, OR BACK OFFICE

    private TransferStatus status;

    private String reason;

    @CreationTimestamp
    private LocalDateTime updatedAt;

}
