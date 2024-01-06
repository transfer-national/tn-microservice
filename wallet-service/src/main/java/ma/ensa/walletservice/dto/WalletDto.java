package ma.ensa.walletservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Data
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class WalletDto {

    private String id;

    private Long clientRef;

    private ClientDto client;

    private Double balance;

    private Double amount;
}