package ma.ensa.transferservice.services;


import lombok.RequiredArgsConstructor;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.users.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static ma.ensa.transferservice.models.enums.TransferStatus.*;

@Component
@RequiredArgsConstructor
public class TransferChecker {

    public void checkServe(Transfer transfer){
        var status = transfer.getStatusDetails().getStatus();

        // get the transfer status
        if(status != TO_SERVE && status != UNBLOCKED_TO_SERVE){
            // TODO: create a custom exception : TransferCannotBeReverted
            throw new RuntimeException("it's not able to revert");
        }
    }


    public void checkRevert(Transfer transfer, String byUser){

        // check the ability to revert
        var sendingDetails = transfer.getSendingDetails();

        var sendingDate = sendingDetails.getUpdatedAt().toLocalDate();
        var userNow = new User(byUser);

        if(!(
                sendingDate.isEqual(LocalDate.now()) &&
                (sendingDetails.getByUser().equals(userNow) || byUser.startsWith("b-")))
        ) {
            throw new RuntimeException("you cannot revert this transfer");
        }
    }

}
