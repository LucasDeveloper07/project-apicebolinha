package io.github.ductvi.apicebolinha.service;

import io.github.ductvi.apicebolinha.controller.dto.TokenDTO;
import io.github.ductvi.apicebolinha.model.User;
import io.github.ductvi.apicebolinha.security.CustomAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public TokenDTO login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        User user = ((CustomAuthentication) auth).getUser();

        return new TokenDTO(tokenService.generated(user), tokenService.getExpirationSeconds());
    }
}
