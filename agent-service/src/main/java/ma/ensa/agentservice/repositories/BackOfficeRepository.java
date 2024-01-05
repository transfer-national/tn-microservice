package ma.ensa.agentservice.repositories;

import ma.ensa.agentservice.models.BackOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackOfficeRepository
        extends JpaRepository<BackOffice, String> {}
