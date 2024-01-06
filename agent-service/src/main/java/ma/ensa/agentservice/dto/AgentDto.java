package ma.ensa.agentservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Data
@JsonInclude(NON_NULL)
public class AgentDto {

    private String createdBy;

    private String name;

    private String address;

    private String email;

    private String phoneNumber;

    private Double balance;

    private Double threshold;

    private String password;

}