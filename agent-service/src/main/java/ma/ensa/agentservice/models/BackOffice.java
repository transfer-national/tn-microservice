package ma.ensa.agentservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder

@Entity
public class BackOffice extends User {

    @ManyToOne
    private Agent agent;

    public BackOffice(String userId){
        super(userId, null);
    }

}