package ma.ensa.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RevertResponseDto {

    private int count;

    private int revertedCount;

    private List<Long> refs;

}
