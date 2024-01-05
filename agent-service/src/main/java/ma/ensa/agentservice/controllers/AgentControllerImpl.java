package ma.ensa.agentservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.agentservice.dto.AgentDto;
import ma.ensa.agentservice.dto.BalanceDto;
import ma.ensa.agentservice.dto.ThresholdDto;
import ma.ensa.agentservice.services.AgentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ValueConstants;

import java.util.List;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentControllerImpl implements AgentController{

    private final AgentService service;


    @Override
    public Object getAgent(String user) {
        if(user != null && !user.equals(ValueConstants.DEFAULT_NONE)){
            return service.getAgent(user);
        }
        return service.getAgents();
    }


    @Override
    public String createAgent(AgentDto dto, String by, boolean bo) {

        dto.setCreatedBy(by);
        return bo ?
                service.createAgentForBackOffice(dto) :
                service.createAgent(dto);
    }

    @Override
    public String updateBalance(BalanceDto dto) {
        return service.updateBalance(dto);
    }

    @Override
    public String updateThreshold(ThresholdDto dto, String byUser) {
        dto.setByUser(byUser);
        return service.updateThreshold(dto);
    }
}