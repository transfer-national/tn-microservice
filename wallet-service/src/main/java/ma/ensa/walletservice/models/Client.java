package ma.ensa.walletservice.models;


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
    private Long ref;

    private String idNumber;

    public Client(Long ref){
        this.ref = ref;
    }

}
