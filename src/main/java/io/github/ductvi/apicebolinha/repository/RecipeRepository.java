package io.github.ductvi.apicebolinha.repository;

import io.github.ductvi.apicebolinha.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    List<Recipe> findByUserId(UUID userId);
}
