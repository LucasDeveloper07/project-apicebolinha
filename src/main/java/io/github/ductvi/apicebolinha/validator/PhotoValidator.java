package io.github.ductvi.apicebolinha.validator;

import io.github.ductvi.apicebolinha.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Component
public class PhotoValidator {

    private static final long MAX_SIZE = 5 * 1024 * 1024;
    private static final Set<String> ALLOWED = Set.of("image/jpeg", "image/png", "image/webp");

    public void validate(MultipartFile file) {
        if (file.getSize() > MAX_SIZE) {
            throw new BadRequestException("A foto deve ter no máximo 5MB!");
        }

        String type = file.getContentType();

        if (type == null || !ALLOWED.contains(type)) {
            throw new BadRequestException("Formato inválido! Use JPG, PNG ou WEBP.");
        }
    }
}
