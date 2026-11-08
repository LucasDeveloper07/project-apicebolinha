package io.github.ductvi.apicebolinha.controller;

import io.github.ductvi.apicebolinha.controller.dto.UserDTO;
import io.github.ductvi.apicebolinha.controller.dto.UserResponseDTO;
import io.github.ductvi.apicebolinha.controller.dto.UserUpdateDTO;
import io.github.ductvi.apicebolinha.controller.mapper.UserMapper;
import io.github.ductvi.apicebolinha.model.User;
import io.github.ductvi.apicebolinha.security.SecurityService;
import io.github.ductvi.apicebolinha.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService service;
    private final UserMapper mapper;
    private final SecurityService securityService;
    private final PasswordEncoder encoder;

    @PostMapping
    @Operation(summary = "Salvar", description = "Salvar novo usuário")
    public ResponseEntity<Void> save(@RequestBody @Valid UserDTO userDto) {
        User user = mapper.toEntity(userDto);
        service.save(user);

        URI location = generatedHeaderLocation(user.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @Operation(summary = "Detalhes Usuário", description = "Ver detalhes do usuário")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") String idUser) {
        UUID id = UUID.fromString(idUser);

        User user = service.getById(id, securityService.loggedUser().getId());

        return ResponseEntity.ok(mapper.toDTO(user));

    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar senha", description = "Atualizar senha do usuário")
    public ResponseEntity<Object> updatePassword(
            @PathVariable("id") String id,
            @RequestBody @Valid UserUpdateDTO userDto) {

        service.updatePassword(UUID.fromString(id), userDto.password(), securityService.loggedUser().getId());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar", description = "Deletar usuário")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        service.delete(UUID.fromString(id), securityService.loggedUser().getId());

        return ResponseEntity.noContent().build();
    }
}
