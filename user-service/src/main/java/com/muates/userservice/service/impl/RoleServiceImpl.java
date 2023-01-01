package com.muates.userservice.service.impl;

import com.muates.userservice.converter.RoleConverter;
import com.muates.userservice.model.dto.request.RoleRequest;
import com.muates.userservice.model.entity.Role;
import com.muates.userservice.repository.RoleRepository;
import com.muates.userservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role addRole(RoleRequest request) {
        return roleRepository.save(RoleConverter.convertToRole(request));
    }

    @Override
    public Role updateRole(Long roleId, RoleRequest request) {
        Role existRole = getRoleById(roleId);

        if (request.getRoleName() != null) {
            existRole.setRoleName(RoleConverter.convertToRoleName(request.getRoleName()));
            existRole.setUpdatedDate(new Date());
            roleRepository.save(existRole);
        }

        return existRole;
    }

    @Override
    public Role getRoleById(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isEmpty())
            throw new RuntimeException();

        return role.get();
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRoleById(Long roleId) {
        Role role = getRoleById(roleId);
        roleRepository.delete(role);
    }
}
