package io.github.ductvi.apicebolinha.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Campo obrigatório!")
        @Email
        String email,

        @NotBlank(message = "Campo obrigatório!")
        String password
) {
}
