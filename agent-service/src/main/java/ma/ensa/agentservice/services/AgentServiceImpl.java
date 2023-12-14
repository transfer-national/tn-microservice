package ma.ensa.agentservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.agentservice.dto.AgentDto;
import ma.ensa.agentservice.dto.BalanceDto;
import ma.ensa.agentservice.dto.ThresholdDto;
import ma.ensa.agentservice.models.Agent;
import ma.ensa.agentservice.models.BackOffice;
import ma.ensa.agentservice.models.ThresholdUpdate;
import ma.ensa.agentservice.repositories.AgentRepository;
import ma.ensa.agentservice.repositories.ThresholdRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static ma.ensa.agentservice.dto.OperationType.*;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService{

    private final AgentRepository agentRepository;
    private final ThresholdRepository thresholdRepository;

    private AgentDto toDto(Agent agent){
        var dto = new AgentDto();
        BeanUtils.copyProperties(agent, dto);
        return dto;
    }

    private Agent getAgentEntity(String id){

        return  agentRepository
            .findById(id)
            .orElseThrow();
            // TODO: create `AgentNotFoundException` class
    }

    @Override
    public List<AgentDto> getAgents() {

        return agentRepository
            .findAll()
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    public AgentDto getAgent(String userId) {

        return toDto(
            getAgentEntity(userId)
        );
    }

    @Override
    public String createAgent(AgentDto dto) {

        var agent = new Agent();

        // copy the primitive properties
        BeanUtils.copyProperties(dto, agent);

        // set backoffice
        agent.setCreatedBy(
            new BackOffice(dto.getCreatedBy())
        );

        agent = agentRepository.save(agent);

        return "CREATED SUCCESSFULLY, agentId: " + agent.getId();
    }

    @Override
    public String updateBalance(BalanceDto dto) {

        // get the agent instance
        var agent = getAgentEntity(dto.getAgentId());

        double balance = agent.getBalance(),
               amount = dto.getAmount(),
               newBalance;

        if(dto.getType() == DEBIT){

            // check the threshold
            if(agent.getThreshold() < amount){
                throw new RuntimeException();
                // TODO: create `ThresholdOverflowException` class
            }

            // check the balance
            if(balance < amount){
                throw new RuntimeException();
                // TODO: create `InsufficientBalanceException` class
            }

            // debit the balance
            newBalance = balance - amount;

        }else{
            // feed the balance
            newBalance = balance + amount;
        }

        // set the update
        agent.setBalance(newBalance);

        // save the update into the database
        agentRepository.save(agent);

        return "BALANCE UPDATED SUCCESSFULLY";
    }

    @Override
    public String updateThreshold(ThresholdDto dto){

        // get the agent entity
        var agent = getAgentEntity(dto.getAgentId());

        var oldThreshold = agent.getThreshold();
        var newThreshold = dto.getNewThreshold();

        // set the new threshold
        agent.setThreshold(newThreshold);

        // save the update into the database
        agentRepository.save(agent);

        // save the update record into the database
        thresholdRepository.save(
            ThresholdUpdate.builder()
                .oldValue(oldThreshold)
                .newValue(newThreshold)
                .agent(agent)
                .updatedBy(new BackOffice(dto.getByUser()))
                .build()
        );

        return "THRESHOLD UPDATED SUCCESSFULLY";

    }

}
