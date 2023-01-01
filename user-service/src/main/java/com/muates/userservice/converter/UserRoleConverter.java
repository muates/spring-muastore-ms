package com.muates.userservice.converter;

import com.muates.userservice.model.dto.response.UserRoleDto;
import com.muates.userservice.model.entity.User;

public class UserRoleConverter {

    public static UserRoleDto convertToResponse(User user) {
        if (user == null)
            return null;

        return UserRoleDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(RoleConverter.convertToResponse(user.getRoles()))
                .build();
    }
}
