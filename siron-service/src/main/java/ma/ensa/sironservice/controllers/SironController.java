package ma.ensa.sironservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.sironservice.models.BlackListedClient;
import ma.ensa.sironservice.services.SironService;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/siron")
@RequiredArgsConstructor
public class SironController {

    private final SironService service;

    @GetMapping("/sender/{clRef}")
    public BlackListedClient checkTransferForSender(
            @PathVariable long clRef
    ){
        return service.isSenderBlackListed(clRef);
    }

    @GetMapping("/transfer/{txRef}")
    public BlackListedClient checkTransferForRecipient(
            @PathVariable long txRef
    ){
        return service.isRecipientBlackListed(txRef);
    }

}
