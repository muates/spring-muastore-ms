package com.muates.userservice.converter;

import com.muates.userservice.model.dto.request.UserRegistrationRequest;
import com.muates.userservice.model.dto.response.UserDto;
import com.muates.userservice.model.entity.User;

import java.util.Date;

public class UserConverter {

    public static User convertToUser(UserRegistrationRequest request) {
        if (request == null)
            return null;

        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .enabled(false)
                .createdDate(new Date())
                .build();
    }

    public static UserDto covertToResponse(User user) {
        if (user == null)
            return null;

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .build();
    }
}
