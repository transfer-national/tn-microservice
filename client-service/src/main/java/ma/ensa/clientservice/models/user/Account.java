package ma.ensa.clientservice.models.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
public abstract class Account extends User {

    public Account(String id){
        super(id, null);
    }

    private double balance;



}
