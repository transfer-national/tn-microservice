package ma.ensa.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public interface TransferResume {

    long getCount();  // param A

    double getTotalAmount(); // param B

}
