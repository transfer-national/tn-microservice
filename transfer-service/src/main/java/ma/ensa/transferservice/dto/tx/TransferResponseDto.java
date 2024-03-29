package ma.ensa.transferservice.dto.tx;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.transferservice.dto.ClientDto;
import ma.ensa.transferservice.dto.RecipientDto;
import ma.ensa.transferservice.models.TransferStatusDetails;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponseDto {

    private long ref;

    private double amount;

    private ClientDto sender;

    private RecipientDto recipient;

    private List<TransferStatusDetails> statuses;
}
