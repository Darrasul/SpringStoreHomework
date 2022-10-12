package com.buzas.springstorehomework.entities.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserDtoMapper {
    @Mapping(target = "password", ignore = true)
    UserDto map(User user);
    @Mapping(target = "id", ignore = true)
    User map(UserDto userDto);
}
