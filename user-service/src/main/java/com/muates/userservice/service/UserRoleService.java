package com.muates.userservice.service;

import com.muates.userservice.model.dto.request.UserRoleRequest;
import com.muates.userservice.model.dto.response.UserRoleDto;

public interface UserRoleService {
    UserRoleDto addRoleToUser(UserRoleRequest request);
    UserRoleDto removeRoleFromUser(UserRoleRequest request);
}
