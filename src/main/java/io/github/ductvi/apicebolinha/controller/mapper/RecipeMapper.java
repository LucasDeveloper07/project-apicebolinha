package io.github.ductvi.apicebolinha.controller.mapper;

import io.github.ductvi.apicebolinha.controller.dto.RecipeDTO;
import io.github.ductvi.apicebolinha.controller.dto.RecipeResponseDTO;
import io.github.ductvi.apicebolinha.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    Recipe toEntity(RecipeDTO recipeDto);

    @Mapping(source = "createdDate", target = "publicationDate")
    RecipeResponseDTO toDTO(Recipe recipe);
}
