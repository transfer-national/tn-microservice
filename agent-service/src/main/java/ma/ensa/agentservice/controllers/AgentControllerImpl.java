package ma.ensa.agentservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.agentservice.dto.AgentDto;
import ma.ensa.agentservice.dto.BalanceDto;
import ma.ensa.agentservice.dto.ThresholdDto;
import ma.ensa.agentservice.services.AgentService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AgentControllerImpl implements AgentController{

    private final AgentService service;

    @Override
    public String test() {
        return "hello agent";
    }

    @Override
    public List<AgentDto> getAgents() {
        return service.getAgents();
    }

    @Override
    public AgentDto getAgent(String userId) {
        return service.getAgent(userId);
    }

    @Override
    public String createAgent(AgentDto dto, String by) {
        dto.setCreatedBy(by);
        return service.createAgent(dto);
    }

    @Override
    public String updateBalance(BalanceDto dto) {
        return service.updateBalance(dto);
    }

    @Override
    public String updateThreshold(ThresholdDto dto, String byUser) {
        System.out.println(dto);
        dto.setByUser(byUser);
        return service.updateThreshold(dto);
    }
}