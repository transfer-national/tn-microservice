package ma.ensa.smsservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


import ma.ensa.smsservice.dto.SMS;
import ma.ensa.smsservice.services.SseService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/sms-gw")
@RequiredArgsConstructor
public class AppGatewayController {

    private final SseService sseService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter emitter(HttpServletRequest request) throws Exception {
        return sseService.add(request);
    }


    @PostMapping("/send")
    public void send(@RequestBody SMS sms) throws IOException {
        sseService.send(sms);
    }

}