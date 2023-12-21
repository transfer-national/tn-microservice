package ma.ensa.transferservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Client {

    @Id
    private long ref;

    private String identity;

    private String gsm;

    public Client(long ref){
        this.ref = ref;
    }

}
