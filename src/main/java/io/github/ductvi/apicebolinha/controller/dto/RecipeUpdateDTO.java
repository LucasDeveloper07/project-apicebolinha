package io.github.ductvi.apicebolinha.controller.dto;

import io.github.ductvi.apicebolinha.model.enums.RecipeCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecipeUpdateDTO(
        @NotBlank(message = "Campo obrigatório!")
        @Size(max = 100, message = "Máximo de 100 caracteres!")
        String title,

        @NotNull(message = "Campo obrigatório!")
        RecipeCategory category,

        String description,

        @NotBlank(message = "Campo obrigatório!")
        String ingredients,

        @NotBlank(message = "Campo obrigatório!")
        String steps
) {}
