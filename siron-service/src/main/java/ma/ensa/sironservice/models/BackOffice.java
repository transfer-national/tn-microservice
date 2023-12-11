package ma.ensa.sironservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BackOffice {

    @Id
    private String id;

}
