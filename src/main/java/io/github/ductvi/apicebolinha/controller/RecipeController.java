package io.github.ductvi.apicebolinha.controller;

import io.github.ductvi.apicebolinha.controller.dto.RecipeDTO;
import io.github.ductvi.apicebolinha.controller.dto.RecipeResponseDTO;
import io.github.ductvi.apicebolinha.controller.dto.RecipeUpdateDTO;
import io.github.ductvi.apicebolinha.controller.mapper.RecipeMapper;
import io.github.ductvi.apicebolinha.model.Recipe;
import io.github.ductvi.apicebolinha.model.enums.RecipeCategory;
import io.github.ductvi.apicebolinha.security.SecurityService;
import io.github.ductvi.apicebolinha.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("recipes")
@RequiredArgsConstructor
public class RecipeController implements GenericController {

    private final RecipeService service;
    private final RecipeMapper mapper;
    private final SecurityService securityService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Salvar", description = "Salvar nova receita")
    public ResponseEntity<Void> save(
            @RequestPart("data") @Valid RecipeDTO recipeDto,
            @RequestPart(value = "photo", required = false)MultipartFile photo) {

        Recipe recipe = mapper.toEntity(recipeDto);
        service.save(recipe, securityService.loggedUser(), photo);

        URI location = generatedHeaderLocation(recipe.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Ver Receita", description = "Ver detalhes da receita")
    public ResponseEntity<RecipeResponseDTO> getDetails(@PathVariable("id") String id) {
        return service
                .findById(UUID.fromString(id))
                .map(recipe -> {
                    RecipeResponseDTO recipeDto = mapper.toDTO(recipe);
                    return ResponseEntity.ok(recipeDto);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PutMapping(path = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Atualizar", description = "Atualizar detalhes da receita")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id,
            @RequestPart("data") @Valid RecipeUpdateDTO recipeDto,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {

        Recipe recipe = service.update(UUID.fromString(id), recipeDto, photo, securityService.loggedUser());

        return ResponseEntity.ok(mapper.toDTO(recipe));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar", description = "Deletar receita")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        return service
                .findById(UUID.fromString(id))
                .map(recipe -> {
                    service.delete(recipe, securityService.loggedUser());
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping
    @Operation(summary = "Pesquisa", description = "Pesquisa páginada")
    public ResponseEntity<Page<RecipeResponseDTO>> search(
            @RequestParam(value = "title", required = false)
            String title,

            @RequestParam(value = "category", required = false)
            RecipeCategory category,

            @RequestParam(value = "page", defaultValue = "0")
            Integer page,

            @RequestParam(value = "page-size", defaultValue = "10")
            Integer pageSize){

        Page<Recipe> pageResult = service.search(title, category, page, pageSize);

        Page<RecipeResponseDTO> pageResponse = pageResult.map(mapper::toDTO);

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/my-recipes")
    @Operation(summary = "Listar receitas do user", description = "Lista todas as receitas do user logado")
    public ResponseEntity<List<RecipeResponseDTO>> getAllRecipesForUser() {
        var listRecipesDto = service
                .findByUserId(securityService.loggedUser().getId())
                .stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(listRecipesDto);
    }
}
