package com.app.vendingmachine.dto.mapper;

import com.app.vendingmachine.dto.UserDto;
import com.app.vendingmachine.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User UserDtoToUser(UserDto userDto);

    UserDto UserToUserDto(User user);

    List<UserDto> UserListToUserDtoList(List<User> userList);
}


