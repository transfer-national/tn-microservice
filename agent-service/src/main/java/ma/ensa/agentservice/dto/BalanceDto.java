package ma.ensa.agentservice.dto;

import lombok.Data;

@Data
public class BalanceDto {

    private String agentId;

    private double amount;

    private OperationType type;

}