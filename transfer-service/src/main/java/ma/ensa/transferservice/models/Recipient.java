package ma.ensa.transferservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Data

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Recipient {

    @Id
    private long id;

    private String phoneNumber;

    @OneToOne
    private Client kycRef;

    public Recipient(long id){
        this.id = id;
    }

}
