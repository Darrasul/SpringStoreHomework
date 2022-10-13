package com.buzas.springstorehomework.entities.users;

import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserDtoMapper {
    @Mapping(target = "password", ignore = true)
    UserDto map(User user);
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "password", target = "password", qualifiedByName = "encode")
    User map(UserDto userDto, @Context PasswordEncoder encoder);
    @Named(value = "encode")
    default String encode(String password, @Context PasswordEncoder encoder) {
        return encoder.encode(password);
    }
}
