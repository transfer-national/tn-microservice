package ma.ensa.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SenderInfo {

    private String firstName;

    private String lastName;

    private String cin;

    private String gsm;

}
