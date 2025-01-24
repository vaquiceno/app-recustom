package com.dashboard.app.mapper;

import com.dashboard.app.dto.UserDto;
import com.dashboard.app.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toModel(UserDto userDto) {
        return User
                .builder()
                .id(userDto.getId())
                .role(userDto.getRole())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .role(user.getRole())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
