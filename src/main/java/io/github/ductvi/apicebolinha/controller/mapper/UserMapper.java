package io.github.ductvi.apicebolinha.controller.mapper;

import io.github.ductvi.apicebolinha.controller.dto.UserDTO;
import io.github.ductvi.apicebolinha.controller.dto.UserResponseDTO;
import io.github.ductvi.apicebolinha.controller.dto.UserUpdateDTO;
import io.github.ductvi.apicebolinha.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDto);
    UserResponseDTO toDTO(User user);
    User toEntityUpdate(UserUpdateDTO userUpdateDTO);
}
