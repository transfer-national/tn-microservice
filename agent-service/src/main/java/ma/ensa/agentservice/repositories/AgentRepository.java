package ma.ensa.agentservice.repositories;

import ma.ensa.agentservice.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository
        extends JpaRepository<Agent, String> {

}
