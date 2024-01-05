package ma.ensa.agentservice.services;

import ma.ensa.agentservice.dto.AgentDto;
import ma.ensa.agentservice.dto.BalanceDto;
import ma.ensa.agentservice.dto.ThresholdDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AgentService {


    List<AgentDto> getAgents();


    AgentDto getAgent(String user);


    String createAgent(AgentDto dto);


    String createAgentForBackOffice(AgentDto dto);

    String updateBalance(BalanceDto dto);


    String updateThreshold(ThresholdDto dto);
}
