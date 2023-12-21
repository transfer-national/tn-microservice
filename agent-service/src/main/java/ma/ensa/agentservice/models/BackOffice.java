package ma.ensa.agentservice.models;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@SuperBuilder
public class BackOffice extends User {

    public BackOffice(String userId){
        super(userId, null);
    }

}