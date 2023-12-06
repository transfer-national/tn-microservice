package ma.ensa.transferservice;


import ma.ensa.transferservice.models.enums.TransferStatusDetail;
import ma.ensa.transferservice.services.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransferServiceTest {

    @Autowired
    private TransferService service;

    @Test
    public void testGetTransferStatus(){

    }


}
