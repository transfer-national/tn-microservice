package ma.ensa.transferservice.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import ma.ensa.transferservice.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/transfer")
@Tag(
        name= "Transfer",
        description = "responsible for sending and manipulating a transfer"
)
public interface TransferController {

    @GetMapping
    List<TransferResponseDto> getAllTransfers(
            @RequestBody SearchFilter filter
    );

    @GetMapping("/{ref}")
    TransferResponseDto getTransfer(
            @PathVariable long ref
    );

    @PostMapping
    @ResponseStatus(CREATED)
    List<Long> emitTransfer(
            @RequestBody SendDto dto
    );

    @PutMapping("/{ref}/{action}")
    Object updateStatus(
        @PathVariable(required = false) Long ref,
        @PathVariable ActionType action,
        @RequestBody TransferDto dto
    );

}
