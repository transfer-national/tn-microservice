package ma.ensa.gateway.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.gateway.dto.AuthPrincipal;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.core.context.ReactiveSecurityContextHolder.*;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationFilter implements WebFilter {


    private final WebClient.Builder webClientBuilder;

    @Override
    public @NotNull Mono<Void> filter(@NotNull ServerWebExchange exchange, @NotNull WebFilterChain chain) {

        // from request get the headers
        var headers = exchange.getRequest().getHeaders();

        // get the token
        if (!headers.containsKey(AUTHORIZATION)) {
            return chain.filter(exchange);
        }

        String token = requireNonNull(headers.get(AUTHORIZATION)).get(0);
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return validateToken(token)
            .flatMap(authPrincipal -> {

                var auth = getAuthToken(authPrincipal);

                var newExchange = addByUserHeader(
                    exchange, authPrincipal.getUserId()
                );

                return chain
                    .filter(newExchange)
                    .contextWrite(withAuthentication(auth));

            }).onErrorComplete();
    }

    private @NotNull Authentication getAuthToken(@NotNull AuthPrincipal principal){


        var grantedAuthority =
                new SimpleGrantedAuthority(principal.getRole().name());

        var auth = new PreAuthenticatedAuthenticationToken(
                principal, null,
                List.of(grantedAuthority)
        );

        auth.setAuthenticated(true);

        return auth;

    }

    private @NotNull ServerWebExchange addByUserHeader(
            @NotNull ServerWebExchange exchange, String userId
    ) {

        var newRequest = exchange.getRequest()
                .mutate()
                .header("By-User", userId)
                .build();

        return exchange.mutate().request(newRequest).build();

    }


    private @NotNull Mono<AuthPrincipal> validateToken(String token) {

        var client = webClientBuilder.baseUrl("lb://auth-service").build();
        return client.get()
                .uri("/auth/validate?token={t}", token)
                .retrieve()
                .bodyToMono(AuthPrincipal.class);
    }
}