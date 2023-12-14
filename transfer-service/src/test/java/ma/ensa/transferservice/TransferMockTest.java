package ma.ensa.transferservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ma.ensa.transferservice.dto.SendDto;
import ma.ensa.transferservice.dto.TransferDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.fasterxml.jackson.databind.type.TypeFactory.*;
import static ma.ensa.transferservice.models.enums.FeeType.*;
import static ma.ensa.transferservice.models.enums.TransferType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class TransferMockTest {


    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private long ref;

    @BeforeEach
    public void initMock(WebApplicationContext wac){
        mockMvc = webAppContextSetup(wac).build();
    }

    // served transfer: 9 426 700 979 615

    @Test @Order(1)
    public void testEmitTransfer() throws Exception {

        var dto = SendDto.builder()
                .senderRef(1L)
                .recipientIds(List.of(1L))
                .amount(1000)
                .feeType(SENDER)
                .transferType(CASH)
                .notificationEnabled(true)
                .reason("gift")
                .userId("a-2840863796") // agent ID
                .build();

        var json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                status().isCreated()
        ).andDo(r -> {
            var result = r.getResponse().getContentAsString();
            List<Long> refs = objectMapper.readValue(
                    result,
                    defaultInstance().constructType(List.class)
            );
            System.out.println("new ref: " + refs);
        });

    }


    @Test @Order(2)
    public void testServeTransfer() throws Exception{

        if(ref == 0L) ref = 3500561670874L;

        var dto = TransferDto.builder()
                .userId("a-2840863796")
                .toWallet(false)
                .reason("null")
                .build();

        var json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
            put("/transfer/{ref}/revert", ref)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );

    }



}
