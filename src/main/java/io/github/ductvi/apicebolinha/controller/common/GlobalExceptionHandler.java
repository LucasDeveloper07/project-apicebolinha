package io.github.ductvi.apicebolinha.controller.common;

import io.github.ductvi.apicebolinha.controller.dto.CustomFieldError;
import io.github.ductvi.apicebolinha.controller.dto.ResponseError;
import io.github.ductvi.apicebolinha.exception.*;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

@RestControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();

        // Field Error (Erro campo)
        List<CustomFieldError> customFieldErrors = fieldErrors
                .stream()
                .map(error -> new CustomFieldError(error.getField(), error.getDefaultMessage()))
                .toList();

        return new ResponseError(HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Erro de validação.",
                customFieldErrors);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleConflictException(ConflictException e) {
        return ResponseError.conflict(e.getMessage());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleRecordNotFoundException(RecordNotFoundException e) {
        return new ResponseError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                List.of());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.CONTENT_TOO_LARGE)
    public ResponseError handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return new ResponseError(HttpStatus.CONTENT_TOO_LARGE.value(),
                "A foto deve ter no máximo 5MB!",
                List.of());
    }

    @ExceptionHandler(StorageException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ResponseError handleStorage(StorageException e) {
        return new ResponseError(HttpStatus.BAD_GATEWAY.value(),
                e.getMessage(),
                List.of());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleBadRequestException(BadRequestException e) {
        return new ResponseError(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                List.of());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handleAuthenticationException(AuthenticationException e) {
        return new ResponseError(
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseError handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseError(
                HttpStatus.FORBIDDEN.value(),
                "Acesso negado!",
                List.of()
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseError handleForbiddenException(ForbiddenException e) {
        return new ResponseError(HttpStatus.FORBIDDEN.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError unhandledErros(RuntimeException e) {
        return new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro inesperado!",
                List.of()
        );
    }
}
