package ma.ensa.agentservice;

import ma.ensa.agentservice.dto.AgentDto;
import ma.ensa.agentservice.dto.BalanceDto;
import ma.ensa.agentservice.dto.ThresholdDto;
import ma.ensa.agentservice.exceptions.AgentNotFoundException;
import ma.ensa.agentservice.exceptions.InsufficientBalanceException;
import ma.ensa.agentservice.models.Agent;
import ma.ensa.agentservice.models.BackOffice;
import ma.ensa.agentservice.models.ThresholdUpdate;
import ma.ensa.agentservice.repositories.AgentRepository;
import ma.ensa.agentservice.repositories.ThresholdRepository;
import ma.ensa.agentservice.services.AgentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ma.ensa.agentservice.dto.OperationType.DEBIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgentServiceImplTest {

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private ThresholdRepository thresholdRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AgentServiceImpl agentService;




    @BeforeEach
    void setUp() {

    }

    @Test
    void getAgents() {
        List<Agent> agents = Arrays.asList(new Agent(), new Agent());
        when(agentRepository.findAll()).thenReturn(agents);

        List<AgentDto> result = agentService.getAgents();

        verify(agentRepository, times(1)).findAll();

        assertNotNull(result);
        assertEquals(agents.size(), result.size());
        // c pour verif que chaque élément de la liste est de type AgentDto
        result.forEach(dto -> assertEquals(AgentDto.class, dto.getClass()));
    }

    @Test
    public void testToDto() {
        BackOffice backOffice=new BackOffice ();

        Agent agent = new Agent();
        agent.setCreatedBy(backOffice);
        agent.setName("Rim MAzzine");
        agent.setAddress("123 adressss");
        agent.setEmail("rim.mz@example.com");
        agent.setPhoneNumber("123456789");
        agent.setBalance(1000.0);
        agent.setThreshold(500.0);
        agent.setPassword("pass");


        AgentDto agentDto = agentService.toDto(agent);


        assertEquals("Rim MAzzine", agentDto.getName());
        assertEquals("123 adressss", agentDto.getAddress());
        assertEquals("rim.mz@example.com", agentDto.getEmail());
        assertEquals("123456789", agentDto.getPhoneNumber());
        assertEquals(1000.0, agentDto.getBalance());
        assertEquals(500.0, agentDto.getThreshold());

    }

    @Test
    void createAgent() {

        AgentDto agentDto = new AgentDto();
        agentDto.setName("testUser");
        agentDto.setCreatedBy("testBackOffice");
        agentDto.setThreshold(500.0);
        agentDto.setBalance(1000.0);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(agentRepository.save(any())).thenReturn(new Agent());


        String result = agentService.createAgent(agentDto);


        verify(passwordEncoder, times(1)).encode("mohcine01");
        verify(agentRepository, times(1)).save(any());
        assertTrue(result.contains("CREATED SUCCESSFULLY"));
    }

    @Test
    //agentById existant et retour d un obj agent
    void getAgentEntity1() {

        String userId = "UserIdExistant";
        Agent expectedAgent = new Agent();
        when(agentRepository.findById(userId)).thenReturn(Optional.of(expectedAgent));


        Agent resultAgent = agentService.getAgentEntity(userId);


        assertNotNull(resultAgent);
        assertEquals(expectedAgent, resultAgent);
        verify(agentRepository, times(1)).findById(userId);
        verify(agentRepository, never()).findByName(anyString());
    }

    @Test
    // angentBYName existant et retour d un obj Agent
    void getAgentEntity2() {

        String userName = "userNameExistant";
        Agent expectedAgent = new Agent();
        when(agentRepository.findByName(userName)).thenReturn(Optional.of(expectedAgent));


        Agent resultAgent = agentService.getAgentEntity(userName);


        assertNotNull(resultAgent);
        assertEquals(expectedAgent, resultAgent);
        verify(agentRepository, times(1)).findByName(userName);

    }


    @Test
    // le cas ou l agent specif n exise pas et lancem d une except
    void getAgentEntity3() {

        String nonExistingUser = "userNonExistant";
        when(agentRepository.findById(nonExistingUser)).thenReturn(Optional.empty());
        when(agentRepository.findByName(nonExistingUser)).thenReturn(Optional.empty());


        assertThrows(AgentNotFoundException.class, () -> agentService.getAgentEntity(nonExistingUser));
        verify(agentRepository, times(1)).findById(nonExistingUser);
        verify(agentRepository, times(1)).findByName(nonExistingUser);
    }


    @Test
     //verif le le cas où le type de l'op est un débit
    // le solde  de l'agent est suffisant pour le montant spécifié
    // et la limite  de l'agent n'est pas dépassée.
    void updateBalance1() {

        String agentId = "agent123";
        double initialBalance = 1000.0;
        double amount = 500.0;
        BalanceDto dto = new BalanceDto();
        dto.setAgentId(agentId);
        dto.setType(DEBIT);
        dto.setAmount(amount);

        Agent agent = new Agent();
        agent.setId(agentId);
        agent.setBalance(initialBalance);
        agent.setThreshold(amount + 100);

        when(agentRepository.findById(agentId)).thenReturn(Optional.of(agent));


        String result = agentService.updateBalance(dto);


        assertEquals("BALANCE UPDATED SUCCESSFULLY", result);
        assertEquals(initialBalance - amount, agent.getBalance());
        verify(agentRepository, times(1)).save(agent);
    }


    @Test
    // agent essaie de débiter un montant supérieur à son solde
    void updateBalance2() {

        String agentId = "agent123";
        double initialBalance = 100.0;
        double amount = 500.0;

        BalanceDto dto = new BalanceDto();
        dto.setAgentId(agentId);
        dto.setType(DEBIT);
        dto.setAmount(amount);

        Agent agent = new Agent();
        agent.setId(agentId);
        agent.setBalance(initialBalance);
        agent.setThreshold(amount + 100);

        when(agentRepository.findById(agentId)).thenReturn(Optional.of(agent));


        assertThrows(InsufficientBalanceException.class, () -> agentService.updateBalance(dto));


        verify(agentRepository, never()).save(any(Agent.class));


        assertEquals(initialBalance, agent.getBalance());
    }


    @Test
    void updateThreshold1() {

        String agentId = "agent123";
        double oldThreshold = 500.0;
        double newThreshold = 700.0;

        ThresholdDto dto = new ThresholdDto();
        dto.setAgentId(agentId);
        dto.setNewThreshold(newThreshold);
        dto.setByUser("admin");

        Agent agent = new Agent();
        agent.setId(agentId);
        agent.setThreshold(oldThreshold);

        when(agentRepository.findById(agentId)).thenReturn(Optional.of(agent));


        String result = agentService.updateThreshold(dto);


        assertEquals("THRESHOLD UPDATED SUCCESSFULLY", result);
        assertEquals(newThreshold, agent.getThreshold());


        verify(agentRepository, times(1)).save(agent);

        ThresholdUpdate expectedThresholdUpdate = new ThresholdUpdate();
        expectedThresholdUpdate.setOldValue(oldThreshold);
        expectedThresholdUpdate.setNewValue(newThreshold);
        expectedThresholdUpdate.setAgent(agent);
        expectedThresholdUpdate.setUpdatedBy(new BackOffice(dto.getByUser()));

        verify(thresholdRepository, times(1)).save(expectedThresholdUpdate);
    }

    @Test
    //cas ou l agent non trouvé
    void updateThreshold2() {
        // Arrange
        String agentId = "agentNonExistant";
        double newThreshold = 700.0;

        ThresholdDto dto = new ThresholdDto();
        dto.setAgentId(agentId);
        dto.setNewThreshold(newThreshold);
        dto.setByUser("admin");

        when(agentRepository.findById(agentId)).thenReturn(Optional.empty());


        assertThrows(AgentNotFoundException.class, () -> agentService.updateThreshold(dto));


        verify(agentRepository, never()).save(any());
        verify(thresholdRepository, never()).save(any());
    }

}
