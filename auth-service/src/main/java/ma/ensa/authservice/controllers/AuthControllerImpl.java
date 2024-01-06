package ma.ensa.authservice.controllers;

import lombok.RequiredArgsConstructor;
import ma.ensa.authservice.dto.AuthRequest;
import ma.ensa.authservice.dto.AuthResponse;
import ma.ensa.authservice.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController{

    private final AuthService service;

    @Override
    public AuthResponse login(AuthRequest dto){
        return service.login(dto);
    }

    @Override
    public AuthResponse validateToken(String token){
        return service.checkToken(token);
    }

    @Override
    public String setPassword(AuthRequest dto) {
        return service.setPassword(dto);
    }

}
