package io.github.ductvi.apicebolinha.validator;

import io.github.ductvi.apicebolinha.exception.ForbiddenException;
import io.github.ductvi.apicebolinha.exception.RecordNotFoundException;
import io.github.ductvi.apicebolinha.model.Recipe;
import io.github.ductvi.apicebolinha.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RecipeValidator {

    private final RecipeRepository repository;

    public void validateExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new RecordNotFoundException("Receita não encontrada!");
        }
    }

    public void validateOwner(Recipe recipe, UUID userId) {
        if (!recipe.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Você não tem permissão para alterar esta receita!");
        }
    }
}
