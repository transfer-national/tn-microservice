package ma.ensa.gateway.config;

import lombok.RequiredArgsConstructor;
import ma.ensa.gateway.filters.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import static org.springframework.security.web.server.context.NoOpServerSecurityContextRepository.*;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final AuthenticationFilter authFilter;

    private final AuthenticationManager authenticationManager;


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
       http
           .csrf(CsrfSpec::disable)
           .httpBasic(HttpBasicSpec::disable)
           .securityContextRepository(getInstance())
           .authenticationManager(authenticationManager)
           .authorizeExchange(ex -> ex
               .anyExchange().permitAll()
           )
           .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }

}
