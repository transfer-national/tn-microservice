package ma.ensa.transferservice.dto;

import lombok.Data;
import ma.ensa.transferservice.models.enums.TransferStatusDetail;

@Data
public class SearchFilter {

    private String identity, gsm;

    private TransferStatusDetail status;

    private String fromDate, toDate;

}