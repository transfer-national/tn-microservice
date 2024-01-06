package ma.ensa.authservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.ensa.authservice.dto.AuthRequest;
import ma.ensa.authservice.dto.AuthResponse;
import ma.ensa.authservice.models.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class AuthServiceApplicationTests {

    // properties
    private static String token;

    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void setup(WebApplicationContext wac){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Order(1) @Test
    public void shouldLogin() throws Exception {

        var dto = AuthRequest.builder()
                .userId("a-4572479942") // change this id
                .password("mohcine01")
                .build();

        var json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
            post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(json)
        ).andDo(
            print()
        ).andExpect(
            status().isOk()
        ).andExpect(
            result -> {
                var json_ = result.getResponse().getContentAsString();
                var dto_ = objectMapper.readValue(json_, AuthResponse.class);
                assertEquals(Role.AGENT, dto_.getRole());
                token = dto_.getToken();
            }
        );

    }

    @Order(2) @Test
    public void shouldValidateToken() throws Exception {


        mockMvc.perform(
                get("/auth/validate?token={t}", token)
        ).andDo(
            print()
        ).andExpect(
            status().isOk()
        );

    }
}