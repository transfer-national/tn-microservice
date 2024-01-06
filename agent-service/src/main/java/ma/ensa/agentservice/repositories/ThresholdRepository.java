package ma.ensa.agentservice.repositories;

import ma.ensa.agentservice.models.ThresholdUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThresholdRepository
        extends JpaRepository<ThresholdUpdate, Long> {}