package ma.ensa.sironservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Client {

    @Id
    private long ref;

    @OneToOne(mappedBy= "client", fetch = FetchType.EAGER)
    private BlackListedClient blc;

}
