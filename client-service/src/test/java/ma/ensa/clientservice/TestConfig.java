package ma.ensa.clientservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TestConfig {


    @Bean
    public MockMvc mockMvc(WebApplicationContext wac){
        return MockMvcBuilders.webAppContextSetup(wac).build();
    }


}
