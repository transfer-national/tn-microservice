package ma.ensa.sironservice.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockReason {

    SENDER("sender is blacklisted"),
    RECIPIENT("recipient is blacklisted");

    private final String reason;

}
