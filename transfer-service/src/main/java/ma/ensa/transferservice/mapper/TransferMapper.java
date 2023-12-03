package ma.ensa.transferservice.mapper;

import ma.ensa.transferservice.dto.TransferDto;
import ma.ensa.transferservice.models.Client;
import ma.ensa.transferservice.models.Recipient;
import ma.ensa.transferservice.models.Transfer;
import org.springframework.beans.BeanUtils;

public class TransferMapper {

    public static Transfer map(TransferDto dto){

        // create a transfer instance
        Transfer transfer = new Transfer(){{

            setRecipient(
                    new Recipient(dto.getRecipientId())
            );

            setSender(
                    new Client(dto.getSenderRef())
            );

            setRecipient(
                    new Recipient(dto.getRecipientId())
            );

        }};

        // copy the properties from dto to record
        BeanUtils.copyProperties(dto, transfer);

        return transfer;

    }


}
