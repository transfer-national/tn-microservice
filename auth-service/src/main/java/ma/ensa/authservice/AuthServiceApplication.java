package ma.ensa.authservice;

import lombok.RequiredArgsConstructor;
import ma.ensa.authservice.models.Agent;
import ma.ensa.authservice.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthServiceApplication
        implements CommandLineRunner{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        var encodedPassword = passwordEncoder.encode("mohcine01");

        var agent = Agent.builder()
                .password(encodedPassword)
                .balance(100_000_000)
                .build();

        userRepository.save(agent);

    }
}
