package io.github.ductvi.apicebolinha.service;

import io.github.ductvi.apicebolinha.model.Favorite;
import io.github.ductvi.apicebolinha.model.Recipe;
import io.github.ductvi.apicebolinha.repository.FavoriteRepository;
import io.github.ductvi.apicebolinha.repository.RecipeRepository;
import io.github.ductvi.apicebolinha.repository.UserRepository;
import io.github.ductvi.apicebolinha.validator.FavoriteValidator;
import io.github.ductvi.apicebolinha.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository repository;
    private final FavoriteValidator validator;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final RecipeRepository recipeRepository;

    @Transactional
    public void create(UUID userId, UUID recipeId) {
        validator.validateCanFavorite(userId, recipeId);

        if (validator.isFavorite(userId, recipeId)) {
            return;
        }

        repository.save(createFavorite(userId, recipeId));
    }

    @Transactional
    public void delete(UUID userId, UUID recipeId) {
        repository.deleteByUserIdAndRecipeId(userId, recipeId);
    }

    public List<Recipe> findFavoriteRecipes(UUID userId) {
        userValidator.validateExists(userId);
        return repository.findRecipesByUserId(userId);
    }

    private Favorite createFavorite(UUID userId, UUID recipeId) {
        Favorite favorite = new Favorite();

        favorite.setUser(userRepository.getReferenceById(userId));
        favorite.setRecipe(recipeRepository.getReferenceById(recipeId));

        return favorite;
    }
}
