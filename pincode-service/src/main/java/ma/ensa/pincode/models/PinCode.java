package ma.ensa.pincode.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
public class PinCode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codepin_seq")
    @SequenceGenerator(name="codepin_seq", allocationSize = 1)
    @JsonProperty(access = WRITE_ONLY)
    private long id;

    private String relatedId; // tx or wallet

    private int pin;

    @JsonProperty(access = WRITE_ONLY)
    private boolean validated; // if the relatedId is for a walletId

    @CreationTimestamp
    @JsonProperty(access = WRITE_ONLY)
    private LocalDateTime createdAt;

    public boolean isExpired(){
        return createdAt
                .plusMinutes(30)
                .isBefore(LocalDateTime.now());
    }

}