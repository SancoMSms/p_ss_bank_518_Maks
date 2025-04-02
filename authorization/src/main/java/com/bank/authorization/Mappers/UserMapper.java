package com.bank.authorization.Mappers;


import com.bank.authorization.DTO.UserDto;
import com.bank.authorization.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);
}
