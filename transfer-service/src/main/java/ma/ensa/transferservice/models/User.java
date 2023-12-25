package ma.ensa.transferservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.InheritanceType.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Inheritance(strategy = JOINED)
public class User{

    @Id
    private String id;

}