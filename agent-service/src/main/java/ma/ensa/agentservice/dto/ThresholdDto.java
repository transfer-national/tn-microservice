package ma.ensa.agentservice.dto;

import lombok.Data;

@Data
public class ThresholdDto {

    private String agentId;

    private String byUser;

    private double newThreshold;

}