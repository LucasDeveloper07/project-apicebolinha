package io.github.ductvi.apicebolinha.repository;

import io.github.ductvi.apicebolinha.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID userId);
    User findByEmail(String email);
}
