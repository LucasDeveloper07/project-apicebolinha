package io.github.ductvi.apicebolinha.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO(
        @NotBlank(message = "Campo obrigatório!")
        String password
) {
}
