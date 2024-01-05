package ma.ensa.agentservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Random;

import static jakarta.persistence.InheritanceType.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Inheritance(strategy = JOINED)
@Entity
public class User {

    @Id
    private String id;

    private String password;

    @PrePersist
    public void init(){
        Random random = new Random();

        if(id != null) return;

        id = "a-" + random.nextLong(
                1_000_000_000L,
                10_000_000_000L
        );
    }


}