package ma.ensa.transferservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.transferservice.models.Client;
import ma.ensa.transferservice.models.TransferStatusDetail;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponseDto {

    private long ref;

    private double amount;

    private Client sender;

    private Client recipient;

    private List<TransferStatusDetail> statuses;
}
