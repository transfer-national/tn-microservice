package ma.ensa.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CancelResponseDto {

    private int count;

    private int cancelledCount;

    private List<Long> refs;
}
