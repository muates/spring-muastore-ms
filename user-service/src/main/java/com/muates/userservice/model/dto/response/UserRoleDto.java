package com.muates.userservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDto {

    private Long userId;
    private String username;
    private String email;
    private List<RoleDto> roles;
}
