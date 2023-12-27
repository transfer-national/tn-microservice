package ma.ensa.transferservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest
public class TransferTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac){
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void testEmitTransfer(){



    }


}
