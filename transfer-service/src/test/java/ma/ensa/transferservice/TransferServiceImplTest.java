package ma.ensa.transferservice;
import ma.ensa.transferservice.dto.TransferResume;
import ma.ensa.transferservice.dto.tx.ActionType;
import ma.ensa.transferservice.dto.tx.TransferDto;
import ma.ensa.transferservice.exceptions.TransferNotFound;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.TransferStatusDetails;
import ma.ensa.transferservice.models.enums.TransferStatus;
import ma.ensa.transferservice.models.users.User;
import ma.ensa.transferservice.repositories.TransferRepository;
import ma.ensa.transferservice.repositories.TsdRepository;
import ma.ensa.transferservice.services.RestCall;
import ma.ensa.transferservice.services.impl.TransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @Mock
    private TransferRepository transferRepository;
    @Mock
    private TsdRepository tsdRepository;


    @Mock
    private RestCall restCall;


    @InjectMocks
    private TransferServiceImpl transferService;

    @Test
    void saveStatus() {

        long transferRef = 123;
        String userId = "user123";
        String reason = "reason";
        Transfer transfer = new Transfer();
        transfer.setRef(transferRef);

        TransferDto transferDto = new TransferDto();
        transferDto.setUserId(userId);
        transferDto.setReason(reason);
        transferDto.setActionType(ActionType.EMIT);

        TransferStatusDetails statusDetails = TransferStatusDetails.builder()
                .byUser(new User(userId))
                .reason(reason)
                .transfer(transfer)
                .status(TransferStatus.TO_SERVE)
                .build();


        when(tsdRepository.save(statusDetails)).thenReturn(statusDetails);


        transferService.saveStatus(transfer, transferDto);


        verify(tsdRepository, times(1)).save(statusDetails);
        verify(restCall, times(1)).sendStatusViaSMS(transfer, TransferStatus.TO_SERVE);


    }

    @Test
    // ici la référence de transfert spécifiée est trouvée dans le repository
    void getTransferEntity1() {

        long transferRef = 123;
        Transfer transfer = new Transfer();
        transfer.setRef(transferRef);


        when(transferRepository.findById(transferRef)).thenReturn(Optional.of(transfer));


        Transfer result = transferService.getTransferEntity(transferRef);


        assertNotNull(result);
        assertEquals(transferRef, result.getRef());


        verify(transferRepository, times(1)).findById(transferRef);
    }


    @Test
    //la référence de transfert n'est pas spécifiée dans le repository
    void getTransferEntity2() {

        long transferRef = 123;


        when(transferRepository.findById(transferRef)).thenReturn(Optional.empty());


        assertThrows(TransferNotFound.class, () -> transferService.getTransferEntity(transferRef));


        verify(transferRepository, times(1)).findById(transferRef);
    }

    @Test
    void unblockTransfer() {

        TransferDto transferDto = new TransferDto();
        transferDto.setRef(123);

        when(transferRepository.findById(eq(123L))).thenReturn(Optional.empty());


        assertThrows(TransferNotFound.class, () -> transferService.unblockTransfer(transferDto));


        verify(transferRepository, times(1)).findById(123L);
    }

    @Test
    void getTransferResume() {

        long senderRef = 123;
        int lastDays = 7;

        TransferResume mockResume = mock(TransferResume.class);

        when(transferRepository.getTransferResume(eq(senderRef), any(LocalDateTime.class))).thenReturn(mockResume);

        TransferResume result = transferService.getTransferResume(senderRef, lastDays);

        assertNotNull(result);
        assertEquals(mockResume, result);

        verify(transferRepository, times(1)).getTransferResume(eq(senderRef), any(LocalDateTime.class));
    }
}


