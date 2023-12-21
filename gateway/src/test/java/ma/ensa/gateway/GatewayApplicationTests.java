package ma.ensa.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.ensa.gateway.dto.AuthPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureWebTestClient

class GatewayApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    private static String token;


    private WebTestClient client;

    @BeforeEach
    public void setup(WebApplicationContext wac){
        client = WebTestClient.bindToApplicationContext(wac).build();

    }

    @Test
    void shouldLogin() throws Exception {

        var dto = AuthRequest.builder()
                .userId("a-4572479942") // change this id
                .password("mohcine01")
                .build();

        var json = objectMapper.writeValueAsString(dto);

        var response = client.post()
                .uri("/auth/login")
                .contentType(APPLICATION_JSON)
                .bodyValue(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthPrincipal.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        token = response.getToken();


    }

}
