package ma.ensa.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {


    @LoadBalanced
    @Bean
    public WebClient.Builder loadBalancedWCB() {
        return WebClient.builder();
    }

}
