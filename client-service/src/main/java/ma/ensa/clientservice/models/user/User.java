package ma.ensa.clientservice.models.user;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Random;

import static jakarta.persistence.InheritanceType.JOINED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Inheritance(strategy = JOINED)
@Entity
public abstract class User{

    @Id
    private String id;

    private String password;

    @PrePersist
    public void init(){
        Random random = new Random();

        id = "w-" + random.nextLong(
                1_000_000_000L,
                10_000_000_000L
        );
    }


}
