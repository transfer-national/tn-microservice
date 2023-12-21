package ma.ensa.authservice;

import lombok.RequiredArgsConstructor;
import ma.ensa.authservice.models.user.BackOffice;
import ma.ensa.authservice.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthServiceApplication{

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
