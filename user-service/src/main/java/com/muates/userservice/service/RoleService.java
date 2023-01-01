package com.muates.userservice.service;

import com.muates.userservice.model.dto.request.RoleRequest;
import com.muates.userservice.model.entity.Role;

import java.util.List;

public interface RoleService {
    Role addRole(RoleRequest request);
    Role updateRole(Long roleId, RoleRequest request);
    Role getRoleById(Long roleId);
    List<Role> getRoles();
    void deleteRoleById(Long roleId);
}
