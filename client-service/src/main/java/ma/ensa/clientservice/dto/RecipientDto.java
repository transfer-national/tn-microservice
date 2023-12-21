package ma.ensa.clientservice.dto;

import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.clientservice.models.Client;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipientDto {

    private long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Long clientRef; // clientId (for adding)


}

