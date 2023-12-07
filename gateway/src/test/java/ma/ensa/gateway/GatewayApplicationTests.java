package ma.ensa.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .expectBody(AuthResponse.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        token = response.getToken();


    }

}
