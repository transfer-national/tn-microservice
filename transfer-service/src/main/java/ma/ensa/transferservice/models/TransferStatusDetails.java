package ma.ensa.transferservice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import ma.ensa.transferservice.models.enums.TransferStatus;
import ma.ensa.transferservice.models.users.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class TransferStatusDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Transfer transfer;

    @ManyToOne
    private User byUser; // AGENT, WALLET, GAP, OR BACK OFFICE

    private TransferStatus status;

    private String reason;

    @CreationTimestamp
    private LocalDateTime updatedAt;

}
