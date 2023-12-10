package ma.ensa.sironservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.sironservice.models.enums.TransferStatus;
import org.hibernate.annotations.CreationTimestamp;

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

    private TransferStatus status;

    private String reason;

    @CreationTimestamp
    private LocalDateTime updatedAt;

}