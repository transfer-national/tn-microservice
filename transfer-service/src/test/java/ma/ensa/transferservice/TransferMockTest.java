package ma.ensa.transferservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ma.ensa.transferservice.dto.PaymentDto;
import ma.ensa.transferservice.dto.TransferRequestDto;
import ma.ensa.transferservice.models.enums.FeeType;
import ma.ensa.transferservice.models.enums.TransferType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

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

        TransferRequestDto dto = TransferRequestDto.builder()
                .senderRef(1L)
                .recipientId(1L)
                .amount(1000)
                .feeType(FeeType.SENDER)
                .transferType(TransferType.CASH)
                .isNotificationEnabled(true)
                .reason("gift")
                .sentById("a-123") // agent ID
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
            System.out.println("new ref: " + result);
            this.ref = Long.parseLong(result);
        });

    }


    @Test @Order(2)
    public void testServeTransfer() throws Exception{

        if(ref == 0L) ref = 1973939389118L;

        var dto = PaymentDto.builder()
                .userId("a-123")
                .toWallet(false)
                .build();

        var json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                put("/transfer/{ref}/serve", ref)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );

    }



}
