package ma.ensa.transferservice.dto;

import lombok.Data;
import ma.ensa.transferservice.models.enums.TransferStatus;

@Data
public class SearchFilter {

    private String identity, gsm;

    private TransferStatus status;

    private String fromDate, toDate;

}