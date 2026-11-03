package io.github.ductvi.apicebolinha.controller.dto;

public record TokenDTO(
        String token,
        long expiresIn
) { }
