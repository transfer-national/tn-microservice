package ma.ensa.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceDto {

    private String agentId;

    private double amount;

    private OperationType type;

}
