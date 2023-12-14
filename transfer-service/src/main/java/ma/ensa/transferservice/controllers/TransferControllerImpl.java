package ma.ensa.transferservice.controllers;


import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.services.TransferService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static ma.ensa.transferservice.dto.ActionType.*;

@RestController
public class TransferControllerImpl implements TransferController {

    private final TransferService service;

    private final Map<ActionType, Function<TransferDto, ?>> updaters;

    public TransferControllerImpl(TransferService service){

        this.service = service;

        updaters = Map.of(
            SERVE,   service::serveTransfer,
            CANCEL,  service::cancelTransfer,
            REVERT,  service::revertTransfer,
            BLOCK,   service::blockTransfer,
            UNBLOCK, service::unblockTransfer
        );

    }

    public List<TransferResponseDto> getAllTransfers(SearchFilter filter){
        return service.getAllTransfers(filter);
    }

    @Override
    public TransferResume getTransferResumeBySender(long senderId, int lastDays) {
        return service.getTransferResume(senderId, lastDays);
    }

    public TransferResponseDto getTransfer(long ref){
        return service.getTransfer(ref);
    }

    public List<Long> emitTransfer(SendDto dto){
        return service.emitTransfer(dto);
    }

    public Object updateStatus(Long ref, String action, TransferDto dto) {
        if(ref != null) dto.setRef(ref);
        var action_ = Enum.valueOf(ActionType.class, action.toUpperCase());
        dto.setActionType(action_);
        return updaters.get(action_).apply(dto);
    }


}