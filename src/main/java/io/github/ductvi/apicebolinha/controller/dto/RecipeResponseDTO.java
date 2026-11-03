package io.github.ductvi.apicebolinha.controller.dto;

import io.github.ductvi.apicebolinha.model.enums.RecipeCategory;

import java.time.LocalDate;
import java.util.UUID;

public record RecipeResponseDTO(
        UUID id,
        String title,
        RecipeCategory category,
        String description,
        String ingredients,
        String steps,
        String photoUrl,
        LocalDate publicationDate,
        UserResponseDTO user
) {}
