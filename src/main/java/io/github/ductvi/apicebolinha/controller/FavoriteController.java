package io.github.ductvi.apicebolinha.controller;

import io.github.ductvi.apicebolinha.controller.dto.FavoriteDTO;
import io.github.ductvi.apicebolinha.controller.dto.RecipeResponseDTO;
import io.github.ductvi.apicebolinha.controller.mapper.RecipeMapper;
import io.github.ductvi.apicebolinha.security.SecurityService;
import io.github.ductvi.apicebolinha.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService service;
    private final RecipeMapper recipeMapper;
    private final SecurityService securityService;

    @PostMapping
    @Operation(summary = "Salvar", description = "Registrar novo favorito")
    public ResponseEntity<Void> save(@RequestBody @Valid FavoriteDTO dto) {
        service.create(securityService.loggedUser().getId(), dto.recipeId());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{recipeId}")
    @Operation(summary = "Deletar", description = "Remover favorito")
    public ResponseEntity<Object> delete(
            @PathVariable("recipeId") String recipeId) {
        service.delete(securityService.loggedUser().getId(), UUID.fromString(recipeId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar", description = "Listar favoritos")
    public ResponseEntity<List<RecipeResponseDTO>> list() {
        UUID userId = securityService.loggedUser().getId();

        List<RecipeResponseDTO> recipes = service
                .findFavoriteRecipes(userId)
                .stream()
                .map(recipeMapper::toDTO)
                .toList();

        return ResponseEntity.ok(recipes);
    }
}
