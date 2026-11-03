package io.github.ductvi.apicebolinha.validator;

import io.github.ductvi.apicebolinha.exception.ConflictException;
import io.github.ductvi.apicebolinha.exception.ForbiddenException;
import io.github.ductvi.apicebolinha.exception.RecordNotFoundException;
import io.github.ductvi.apicebolinha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public void validateExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new RecordNotFoundException("Usuário não encontrado!");
        }
    }

    public void validateEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new ConflictException("Usuário já existente!");
        }
    }

    public void validateEmail(String email, UUID userId) {
        if (repository.existsByEmailAndIdNot(email, userId)) {
            throw new ConflictException("E-mail já cadastrado!");
        }
    }

    public void validateSelf(UUID id, UUID loggedUserId) {
        if (!id.equals(loggedUserId)) {
            throw new ForbiddenException("Você só pode acessar a sua própria conta!");
        }
    }
}
