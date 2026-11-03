package io.github.ductvi.apicebolinha.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FavoriteDTO(
        @NotNull(message = "Campo obrigatório!")
        UUID recipeId
) {
}
