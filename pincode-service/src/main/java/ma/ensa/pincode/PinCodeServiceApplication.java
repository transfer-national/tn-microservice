package ma.ensa.pincode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PinCodeServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(PinCodeServiceApplication.class, args);
    }

}
