package io.github.ductvi.apicebolinha.service;

import io.github.ductvi.apicebolinha.exception.RecordNotFoundException;
import io.github.ductvi.apicebolinha.model.User;
import io.github.ductvi.apicebolinha.repository.UserRepository;
import io.github.ductvi.apicebolinha.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserValidator validator;
    private final PasswordEncoder encoder;

    @Transactional
    public User save(User user) {
        user.setEmail(normalize(user.getEmail()));
        validator.validateEmail(user.getEmail());
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User getById(UUID id, UUID loggedUserId) {
        validator.validateSelf(id, loggedUserId);
        return repository.findById(id).orElseThrow( () -> new RecordNotFoundException("Usuário não encontrado!"));
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void updatePassword(UUID id, String newPassword, UUID loggedUserId) {
        validator.validateSelf(id, loggedUserId);

        User user = repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Usuário não encontrado!"));

        user.setPassword(encoder.encode(newPassword));
    }

    @Transactional
    public void delete(UUID id, UUID loggedUserId) {
        validator.validateSelf(id, loggedUserId);
        validator.validateExists(id);
        repository.deleteById(id);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(normalize(email));
    }

    private String normalize(String email) {
        return email.trim().toLowerCase();
    }
}
