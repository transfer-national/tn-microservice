package ma.ensa.agentservice.controllers;

import ma.ensa.agentservice.dto.AgentDto;
import ma.ensa.agentservice.dto.BalanceDto;
import ma.ensa.agentservice.dto.ThresholdDto;
import org.springframework.web.bind.annotation.*;
import ma.ensa.agentservice.exceptions.NameNotAvailableException;
import java.util.List;
import java.util.Objects;


public interface AgentController {


    // user = id || user == name
    @GetMapping
    Object getAgent(
        @RequestParam(value = "user", required = false) String user // name or id
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
