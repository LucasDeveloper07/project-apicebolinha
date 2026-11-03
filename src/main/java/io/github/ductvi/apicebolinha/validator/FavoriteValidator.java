package io.github.ductvi.apicebolinha.validator;

import io.github.ductvi.apicebolinha.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FavoriteValidator {

    private final FavoriteRepository repository;
    private final UserValidator userValidator;
    private final RecipeValidator recipeValidator;

    public void validateCanFavorite(UUID userId, UUID recipeId) {
        userValidator.validateExists(userId);
        recipeValidator.validateExists(recipeId);
    }

    public boolean isFavorite(UUID userId, UUID recipeId) {
        return repository.existsByUserIdAndRecipeId(userId, recipeId);
    }
}
