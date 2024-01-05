package ma.ensa.agentservice.services;

import lombok.RequiredArgsConstructor;
import ma.ensa.agentservice.dto.AgentDto;
import ma.ensa.agentservice.dto.BalanceDto;
import ma.ensa.agentservice.dto.ThresholdDto;
import ma.ensa.agentservice.exceptions.AgentNotFoundException;
import ma.ensa.agentservice.exceptions.InsufficientBalanceException;
import ma.ensa.agentservice.exceptions.ThresholdOverflowException;
import ma.ensa.agentservice.models.Agent;
import ma.ensa.agentservice.models.BackOffice;
import ma.ensa.agentservice.models.ThresholdUpdate;
import ma.ensa.agentservice.repositories.AgentRepository;
import ma.ensa.agentservice.repositories.BackOfficeRepository;
import ma.ensa.agentservice.repositories.ThresholdRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static ma.ensa.agentservice.dto.OperationType.*;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService{

    private final AgentRepository agentRepository;
    private final ThresholdRepository thresholdRepository;
    private final BackOfficeRepository backOfficeRepository;
    private final PasswordEncoder passwordEncoder;

    private AgentDto toDto(Agent agent){
        var dto = new AgentDto();
        BeanUtils.copyProperties(agent, dto);
        return dto;
    }

    private Agent getAgentEntity(String user){

        return  agentRepository
            .findById(user)
            .or(() -> agentRepository.findByName(user))
            .orElseThrow(AgentNotFoundException::new);
    }

    @Override
    public List<AgentDto> getAgents() {

        return agentRepository
            .findAllByCreatedByIsNotNull()
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    public AgentDto getAgent(String user) {
        return toDto(getAgentEntity(user));
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

        // set the encoded password
        agent.setPassword(
            passwordEncoder.encode("mohcine01")
        );

        agent = agentRepository.save(agent);

        return "CREATED SUCCESSFULLY, agentId: " + agent.getId();
    }

    @Override
    public String createAgentForBackOffice(AgentDto dto){

        var agent = new Agent();

        // copy the primitive properties
        BeanUtils.copyProperties(dto, agent);

        // set the id similar to backoffice id
        var bid = dto.getCreatedBy();
        var aid = bid.replace("b-", "a-");

        agent.setId(aid);

        // find the back office
        var backOffice = backOfficeRepository
                .findById(bid)
                .orElseThrow();

        agent = agentRepository.save(agent);

        backOffice.setAgent(agent);

        backOfficeRepository.save(backOffice);

        return "CREATED SUCCESSFULLY for back office, agentId : " + agent.getId();
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
                throw new ThresholdOverflowException();
            }

            // check the balance
            if(balance < amount){
                throw new InsufficientBalanceException();
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
