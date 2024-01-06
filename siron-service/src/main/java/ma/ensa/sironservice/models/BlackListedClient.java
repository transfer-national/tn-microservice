package ma.ensa.sironservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class BlackListedClient {

    @Id @GeneratedValue
    private long ref;

    @OneToOne
    private Client client;

    @OneToOne
    private BackOffice byUser;

    private String reason;

    private boolean unlisted;
}
