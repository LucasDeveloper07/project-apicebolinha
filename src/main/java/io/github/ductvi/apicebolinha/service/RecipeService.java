package io.github.ductvi.apicebolinha.service;

import io.github.ductvi.apicebolinha.controller.dto.RecipeUpdateDTO;
import io.github.ductvi.apicebolinha.exception.RecordNotFoundException;
import io.github.ductvi.apicebolinha.model.Recipe;
import io.github.ductvi.apicebolinha.model.User;
import io.github.ductvi.apicebolinha.repository.RecipeRepository;
import io.github.ductvi.apicebolinha.repository.UserRepository;
import io.github.ductvi.apicebolinha.validator.PhotoValidator;
import io.github.ductvi.apicebolinha.validator.RecipeValidator;
import io.github.ductvi.apicebolinha.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository repository;
    private final RecipeValidator validator;

    private final PhotoValidator photoValidator;
    private final PhotoStorageService storage;

    @Transactional
    public Recipe save(Recipe recipe, User user, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            photoValidator.validate(photo);
        }

        recipe.setUser(user);
        repository.save(recipe);

        if (photo != null && !photo.isEmpty()) {
            recipe.setPhotoUrl(storage.upload(recipe.getId(), photo));
        }

        return recipe;
    }

    public Optional<Recipe> findById(UUID id) {
        validator.validateExists(id);
        return repository.findById(id);
    }

    @Transactional
    public Recipe update(UUID id, RecipeUpdateDTO dto, MultipartFile photo, User user) {
        Recipe recipe = repository.findById(id)
                .orElseThrow( () -> new RecordNotFoundException("Receita não encontrada!"));
        validator.validateOwner(recipe, user.getId());

        if (photo != null && !photo.isEmpty()) {
            photoValidator.validate(photo);
        }

        recipe.setTitle(dto.title());
        recipe.setCategory(dto.category());
        recipe.setDescription(dto.description());
        recipe.setIngredients(dto.ingredients());
        recipe.setSteps(dto.steps());

        if (photo != null && !photo.isEmpty()) {
            recipe.setPhotoUrl(storage.upload(id, photo));
        }

        return repository.save(recipe); // Dirty checking também pode gravar no commit sem save()
    }

    @Transactional
    public void delete(Recipe recipe, User user) {
        validator.validateExists(recipe.getId());
        validator.validateOwner(recipe, user.getId());
        repository.delete(recipe);
    }

    public List<Recipe> findByUserId(UUID userId) {
        return repository.findByUserId(userId);
    }
}
