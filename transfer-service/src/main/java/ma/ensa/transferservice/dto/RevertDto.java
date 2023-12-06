package ma.ensa.transferservice.dto;

import lombok.Data;

@Data
public class RevertDto {

    private long ref;

    private String reason;

    private String userId;

}
