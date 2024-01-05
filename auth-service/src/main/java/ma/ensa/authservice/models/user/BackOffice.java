package ma.ensa.authservice.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder

@Entity
public class BackOffice extends User{


    @ManyToOne
    private Agent agent;

    @Override
    public String getName() {
        return agent.getName();
    }
}
