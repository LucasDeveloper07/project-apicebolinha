package io.github.ductvi.apicebolinha.repository;

import io.github.ductvi.apicebolinha.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {

    List<Recipe> findByUserId(UUID userId);
}
