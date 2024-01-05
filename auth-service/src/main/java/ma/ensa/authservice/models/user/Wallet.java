package ma.ensa.authservice.models.user;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@NoArgsConstructor
@SuperBuilder

@Entity
public class Wallet extends Account{

    @Override
    public String getName() {
        return "wallet";
    }
}
