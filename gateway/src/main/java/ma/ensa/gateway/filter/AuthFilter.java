package ma.ensa.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.gateway.exceptions.MissingAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerWebClientBuilderBeanPostProcessor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.util.Objects.*;
import static org.springframework.http.HttpHeaders.*;

@Component
@Log4j2
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

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

        var path = exchange.getRequest().getURI().getPath();

        log.debug("{} = {}", NAME_KEY, VALUE_KEY);

        // check if the end point is open
        if(validator.isOpenEndpoint(path))
            return chain.filter(exchange);


        var headers = exchange.getRequest().getHeaders();

        // get the token
        var token = getToken(headers);

        return validateToken(token)
                .doOnNext(s -> {
                    char role = s.charAt(0);
                    validator.validateByRole(path, role);

                })
                .then(chain.filter(exchange));
    }

    private String getToken(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION)) {
            throw new MissingAuthToken();
        }

        String authHeader = requireNonNull(headers.get(AUTHORIZATION)).get(0);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
        }
        return authHeader;
    }

    private Mono<String> validateToken(String token){

        WebClient client = webClientBuilder.baseUrl("lb://auth-service").build();
        return  client.get()
                .uri("/auth/validate?token={t}", token)
                .retrieve()
                .bodyToMono(String.class);
    }

    private String getAuthServicePath(){
        return loadBalancerClient
                .choose("auth-service")
                .getUri()
                .getPath();
    }

    public static class Config{
        // add custom configurations
    }
}