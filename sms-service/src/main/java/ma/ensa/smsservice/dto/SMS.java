package ma.ensa.smsservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SMS {

    @Pattern(
            regexp = "^(\\+212|0)[67][0-9]{8}$",
            message = "you must provide a valid phone number"
    )
    private String phoneNumber;

    @NotNull(message = "you must provide the body string")
    private String body;


}