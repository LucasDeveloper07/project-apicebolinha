package io.github.ductvi.apicebolinha.controller;

import io.github.ductvi.apicebolinha.controller.dto.LoginDTO;
import io.github.ductvi.apicebolinha.controller.dto.TokenDTO;
import io.github.ductvi.apicebolinha.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login (@RequestBody LoginDTO loginDto) {
        TokenDTO tokenDto = service.login(loginDto.email(), loginDto.password());

        return ResponseEntity.ok(tokenDto);
    }
}
