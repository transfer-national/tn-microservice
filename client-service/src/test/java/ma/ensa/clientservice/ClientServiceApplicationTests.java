package ma.ensa.clientservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.ensa.clientservice.dto.ClientDto;
import ma.ensa.clientservice.models.enums.IdType;
import ma.ensa.clientservice.models.enums.Title;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest

class ClientServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldAddClient() throws Exception{

        ClientDto dto = new ClientDto(
            0L,
            Title.M,
            "Mohcine", "SAHTANI",
            IdType.CIN, "Morocco",
            "HH125495", LocalDate.of(2027, 11, 7),
            LocalDate.of(2001, 1, 19), "Student",
            "Moroccan", "8, BLOC A, BLED EL JED", "SAFI",
            "Morocco", "212704261627", "mohcine.sahtani@gmail.com"
        );


        mockMvc.perform(
            post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        ).andDo(
            print()
        )
        .andExpect(
            status().isCreated()
        );


    }

}
