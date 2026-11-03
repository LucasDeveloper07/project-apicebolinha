package io.github.ductvi.apicebolinha.repository;

import io.github.ductvi.apicebolinha.model.Favorite;
import io.github.ductvi.apicebolinha.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    void deleteByUserIdAndRecipeId(UUID userId, UUID recipeId);

    boolean existsByUserIdAndRecipeId(UUID userId, UUID recipeId);

    @Query("""
            SELECT r FROM Favorite f
            JOIN f.recipe r
            WHERE f.user.id = :userId
            ORDER BY r.title
            """)
    List<Recipe> findRecipesByUserId(@Param("userId") UUID userId);
}
