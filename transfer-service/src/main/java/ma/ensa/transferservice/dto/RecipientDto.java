package ma.ensa.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RecipientDto {

    private long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

}

