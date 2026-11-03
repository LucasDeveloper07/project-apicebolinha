package io.github.ductvi.apicebolinha.controller;

import io.github.ductvi.apicebolinha.controller.dto.RecipeDTO;
import io.github.ductvi.apicebolinha.controller.dto.RecipeResponseDTO;
import io.github.ductvi.apicebolinha.controller.dto.RecipeUpdateDTO;
import io.github.ductvi.apicebolinha.controller.mapper.RecipeMapper;
import io.github.ductvi.apicebolinha.model.Recipe;
import io.github.ductvi.apicebolinha.security.SecurityService;
import io.github.ductvi.apicebolinha.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("recipes")
@RequiredArgsConstructor
public class RecipeController implements GenericController {

    private final RecipeService service;
    private final RecipeMapper mapper;
    private final SecurityService securityService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> save(
            @RequestPart("data") @Valid RecipeDTO recipeDto,
            @RequestPart(value = "photo", required = false)MultipartFile photo) {

        Recipe recipe = mapper.toEntity(recipeDto);
        service.save(recipe, securityService.loggedUser(), photo);

        URI location = generatedHeaderLocation(recipe.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipeResponseDTO> getDetails(@PathVariable("id") String id) {
        return service
                .findById(UUID.fromString(id))
                .map(recipe -> {
                    RecipeResponseDTO recipeDto = mapper.toDTO(recipe);
                    return ResponseEntity.ok(recipeDto);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PutMapping(path = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> update(
            @PathVariable("id") String id,
            @RequestPart("data") @Valid RecipeUpdateDTO recipeDto,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {

        Recipe recipe = service.update(UUID.fromString(id), recipeDto, photo, securityService.loggedUser());

        return ResponseEntity.ok(mapper.toDTO(recipe));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        return service
                .findById(UUID.fromString(id))
                .map(recipe -> {
                    service.delete(recipe, securityService.loggedUser());
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
