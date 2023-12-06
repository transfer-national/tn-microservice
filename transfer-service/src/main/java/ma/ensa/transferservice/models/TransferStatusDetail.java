package ma.ensa.transferservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class TransferStatusDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @ManyToOne
    private Transfer transfer;

    @ManyToOne
    private User byUser; // AGENT, WALLET, GAP, OR BACK OFFICE

    private ma.ensa.transferservice.models.enums.TransferStatusDetail status;

    private String reason;

    @CreationTimestamp
    private LocalDateTime updatedAt;

}
