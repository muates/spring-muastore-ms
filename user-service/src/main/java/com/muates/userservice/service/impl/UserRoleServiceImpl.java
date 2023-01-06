package com.muates.userservice.service.impl;

import com.muates.userservice.converter.RoleConverter;
import com.muates.userservice.converter.UserRoleConverter;
import com.muates.userservice.model.dto.request.UserRoleRequest;
import com.muates.userservice.model.dto.response.UserRoleDto;
import com.muates.userservice.model.entity.Role;
import com.muates.userservice.model.entity.User;
import com.muates.userservice.repository.RoleRepository;
import com.muates.userservice.repository.UserRepository;
import com.muates.userservice.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserRoleDto addRoleToUser(UserRoleRequest request) {
        Optional<User> user = userRepository.findUserByUsername(request.getUsername());
        Optional<Role> role = roleRepository.findRoleByRoleName(RoleConverter.convertToRoleName(request.getRoleName()));

        if (user.isEmpty() || role.isEmpty())
            throw new RuntimeException();

        user.get().getRoles().add(role.get());
        user.get().setUpdatedDate(new Date());

        return UserRoleConverter.convertToResponse(userRepository.save(user.get()));
    }

    @Override
    public UserRoleDto removeRoleFromUser(UserRoleRequest request) {
        Optional<User> user = userRepository.findUserByUsername(request.getUsername());
        Optional<Role> role = roleRepository.findRoleByRoleName(RoleConverter.convertToRoleName(request.getRoleName()));

        if (user.isEmpty() || role.isEmpty())
            throw new RuntimeException();

        user.get().getRoles().remove(role.get());
        user.get().setUpdatedDate(new Date());

        return UserRoleConverter.convertToResponse(userRepository.save(user.get()));
    }
}
