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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.web.server.context.NoOpServerSecurityContextRepository.*;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final AuthenticationFilter authFilter;

    private final AuthenticationManager authenticationManager;


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http){
       http
           .csrf(CsrfSpec::disable)
           .httpBasic(HttpBasicSpec::disable)
           .securityContextRepository(getInstance())
               .cors(c -> c.configurationSource(corsConfigurationSource()))
           .authenticationManager(authenticationManager)
           .authorizeExchange(ex -> ex
               .anyExchange().permitAll()
           )
           .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }


    private CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
