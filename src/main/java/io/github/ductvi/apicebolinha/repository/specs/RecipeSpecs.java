package io.github.ductvi.apicebolinha.repository.specs;

import io.github.ductvi.apicebolinha.model.Recipe;
import io.github.ductvi.apicebolinha.model.enums.RecipeCategory;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecs {

    public static Specification<Recipe> titleLike(String title) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Recipe> categoryEqual(RecipeCategory category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }
}
