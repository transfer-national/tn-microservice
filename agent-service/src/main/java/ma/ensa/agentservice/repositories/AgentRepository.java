package ma.ensa.agentservice.repositories;

import ma.ensa.agentservice.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface AgentRepository
        extends JpaRepository<Agent, String> {

    Optional<Agent> findByName(String name);

    List<Agent> findAllByCreatedByIsNotNull();


}
