package io.github.ductvi.apicebolinha.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "Campo obrigatório!")
        @Size(min = 1, max = 100)
        String name,

        @NotBlank(message = "Campo obrigatório!")
        @Size(max = 255)
        @Email
        String email,

        @NotBlank(message = "Campo obrigatório!")
        @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres!")
        String password
) {
}
