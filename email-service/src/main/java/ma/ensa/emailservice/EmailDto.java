package ma.ensa.emailservice;

import lombok.Data;

@Data
public class EmailDto {

    private String to;

    private String subject;

    private String body;

}