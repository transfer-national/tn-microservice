package ma.ensa.agentservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;

@Data
@NoArgsConstructor
@SuperBuilder

@Entity
public class ThresholdUpdate {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "threshold_update_seq")
    @SequenceGenerator(name="threshold_update_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    private Agent agent;

    @ManyToOne
    private BackOffice updatedBy;

    private double oldValue;

    private double newValue;

    @CreationTimestamp
    private LocalDateTime updatedAt;

}