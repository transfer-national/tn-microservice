package ma.ensa.authservice.repositories;

import ma.ensa.authservice.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, String> {
}
