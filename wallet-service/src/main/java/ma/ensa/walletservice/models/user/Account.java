package ma.ensa.walletservice.models.user;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder

@Entity
public class Account extends User {

    private double balance;

}
