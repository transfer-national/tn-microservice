package ma.ensa.clientservice.dto;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.clientservice.entities.Client;

import java.util.Collection;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class ReceipientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @OneToOne
    private Client client;


}

