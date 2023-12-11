package ma.ensa.transferservice.controllers;


import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.services.TransferService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static ma.ensa.transferservice.dto.ActionType.*;


public class TransferControllerImpl implements TransferController {


    private final TransferService service;

    private final Map<ActionType, Function<TransferDto , ?>> updaters;

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

    @Override
    public List<TransferResponseDto> getAllTransfers(SearchFilter filter){
        return service.getAllTransfers(filter);
    }

    @Override
    public TransferResponseDto getTransfer(long ref){
        return service.getTransfer(ref);
    }

    @Override
    public List<Long> emitTransfer(SendDto dto){
        return service.emitTransfer(dto);
    }

    @Override
    public Object updateStatus(Long ref, ActionType action, TransferDto dto) {
        dto.setRef(ref);
        dto.setActionType(action);
        return updaters.get(action).apply(dto);
    }


}