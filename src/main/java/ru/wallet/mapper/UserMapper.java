package ru.wallet.mapper;

import org.mapstruct.Mapper;
import ru.wallet.domain.User;
import ru.wallet.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserDto userDto);
}
