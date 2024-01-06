package ma.ensa.clientservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.clientservice.models.user.Agent;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;

@Data
@NoArgsConstructor
@SuperBuilder

@Entity
public class ClientUpdate {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "cu_seq")
    @SequenceGenerator(name="cu_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Agent updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String fileUrl;
}
