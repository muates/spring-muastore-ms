package com.muates.userservice.converter;

import com.muates.userservice.model.dto.request.RoleRequest;
import com.muates.userservice.model.dto.response.RoleDto;
import com.muates.userservice.model.entity.Role;
import com.muates.userservice.model.enums.RoleName;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RoleConverter {

    public static Role convertToRole(RoleRequest request) {
        if (request == null)
            return null;

        return Role.builder()
                .roleName(convertToRoleName(request.getRoleName()))
                .createdDate(new Date())
                .build();
    }

    public static RoleDto convertToResponse(Role role) {
        if (role == null)
            return null;

        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName().toString())
                .createdDate(role.getCreatedDate())
                .updatedDate(role.getUpdatedDate())
                .build();
    }

    public static List<RoleDto> convertToResponse(List<Role> roles) {
        if (roles.isEmpty())
            return Collections.emptyList();

        return roles.stream()
                .map(RoleConverter::convertToResponse)
                .collect(Collectors.toList());
    }

    public static RoleName convertToRoleName(String roleName) {
        if (roleName == null)
            return null;

        switch (roleName) {
            case "ROLE_USER":
                return RoleName.ROLE_USER;
            case "ROLE_ADMIN":
                return RoleName.ROLE_ADMIN;
            case "ROLE_SELLER":
                return RoleName.ROLE_SELLER;
            default:
                return null;
        }
    }
}
