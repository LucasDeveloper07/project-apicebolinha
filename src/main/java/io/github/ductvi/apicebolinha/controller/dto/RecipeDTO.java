package io.github.ductvi.apicebolinha.controller.dto;

import io.github.ductvi.apicebolinha.model.enums.RecipeCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RecipeDTO(
        @NotBlank(message = "Campo obrigatório!")
        @Size(max = 100, message = "Máximo de 100 caracteres!")
        String title,

        @NotNull
        RecipeCategory category,

        String description,

        @NotBlank(message = "Campo obrigatório!")
        String ingredients,

        @NotBlank(message = "Campo obrigatório!")
        String steps
) {
}
