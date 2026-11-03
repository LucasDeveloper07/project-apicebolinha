package io.github.ductvi.apicebolinha.security;

import io.github.ductvi.apicebolinha.model.User;
import io.github.ductvi.apicebolinha.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (shouldConverter(authentication)) {
            UUID userId = UUID.fromString(authentication.getName());

            Optional<User> userOptional = userService.findById(userId);

            if (userOptional.isPresent()) {
                SecurityContextHolder.getContext().setAuthentication(new CustomAuthentication(userOptional.get()));
            } else {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldConverter(Authentication authentication) {
        return authentication instanceof JwtAuthenticationToken;
    }
}
