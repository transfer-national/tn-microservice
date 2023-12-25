package ma.ensa.transferservice.models.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.InheritanceType.JOINED;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Inheritance(strategy = JOINED)
public class User{

    @Id
    private String id;

}
