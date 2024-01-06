package ma.ensa.walletservice.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.ensa.walletservice.models.Client;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder

@Entity
public class Wallet extends Account{

    @OneToOne
    private Client client;

}
