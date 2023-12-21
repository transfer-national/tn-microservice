package ma.ensa.agentservice.controllers;

import ma.ensa.agentservice.dto.AgentDto;
import ma.ensa.agentservice.dto.BalanceDto;
import ma.ensa.agentservice.dto.ThresholdDto;
import org.springframework.web.bind.annotation.*;
import ma.ensa.agentservice.exceptions.NameNotAvailableException;
import java.util.List;

@RestController
@RequestMapping("/agent")
public interface AgentController {

    @GetMapping("/test")
    String test();

    @GetMapping
    List<AgentDto> getAgents();

    @GetMapping("/{userId}")
    AgentDto getAgent(
        @PathVariable String userId
    );

    @PostMapping
    String createAgent(
        @RequestBody AgentDto dto,
        @RequestHeader("By-User") String by
    );

    // TODO: create a put mapping to update the agent info

    @PutMapping("/balance")
    String updateBalance(
            @RequestBody BalanceDto dto
    );

    @PutMapping("/threshold")
    String updateThreshold(
            @RequestBody ThresholdDto dto,
            @RequestHeader("By-User") String by
    );

}
