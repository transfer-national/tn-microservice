package ma.ensa.gateway.filters;

import lombok.extern.log4j.Log4j2;
import ma.ensa.gateway.config.AuthorizedPathConfig;
import ma.ensa.gateway.dto.AuthResponse;
import ma.ensa.gateway.exceptions.MissingAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.requireNonNull;
import static ma.ensa.gateway.filters.AuthFilter.Config;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Log4j2
public class AuthFilter extends AbstractGatewayFilterFactory<Config> {

    @Autowired
    private AuthorizedPathConfig authorizedPath;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public AuthFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return this::filter;
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // from request get path and the headers
        var path = exchange.getRequest().getURI().getPath();
        var headers = exchange.getRequest().getHeaders();

        // get the token
        var token = getToken(headers);

        // validate the token and check the authorization
        // by role
        return validateToken(token)
            .doOnNext(r ->
                authorizedPath.validateByRole(path, r.getRole())
            ).then(
                chain.filter(exchange)
            );
    }

    private String getToken(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION)) {
            throw new MissingAuthToken();
        }

        String authHeader = requireNonNull(headers.get(AUTHORIZATION)).get(0);
        if (authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
        }
        return authHeader;
    }

    private Mono<AuthResponse> validateToken(String token){

        var client = webClientBuilder.baseUrl("lb://auth-service").build();
        return  client.get()
                .uri("/auth/validate?token={t}", token)
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }

    public static class Config{
        // add custom configurations
    }
}