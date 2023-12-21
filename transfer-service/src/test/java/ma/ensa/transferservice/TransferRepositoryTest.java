package ma.ensa.transferservice;

import ma.ensa.transferservice.repositories.TransferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class TransferRepositoryTest {

    @Autowired
    private TransferRepository repository;


    @Test
    public void shouldRun(){
        var last = LocalDateTime.now().minusDays(14);
        var result = repository.getTransferResume(1, last);
        System.out.println(
                result.getCount() + " --- " + result.getTotalAmount()
        );

    }


}
