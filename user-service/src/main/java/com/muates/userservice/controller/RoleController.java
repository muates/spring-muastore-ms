package com.muates.userservice.controller;

import com.muates.userservice.converter.RoleConverter;
import com.muates.userservice.model.dto.request.RoleRequest;
import com.muates.userservice.model.dto.response.RoleDto;
import com.muates.userservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.muates.userservice.controller.endpoint.RoleEndpoint.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class RoleController {

    private final RoleService roleService;

    @PostMapping(ADD_ROLE)
    public RoleDto addRole(@RequestBody @Valid RoleRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(ADD_ROLE).toUriString());
        return ResponseEntity.created(uri)
                .body(RoleConverter.convertToResponse(roleService.addRole(request)))
                .getBody();
    }

    @PutMapping(UPDATE_ROLE)
    public RoleDto updateRole(@PathVariable Long roleId,
                              @RequestBody @Valid RoleRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(UPDATE_ROLE).toUriString());
        return ResponseEntity.created(uri)
                .body(RoleConverter.convertToResponse(roleService.updateRole(roleId, request)))
                .getBody();
    }

    @GetMapping(GET_ROLE)
    public RoleDto getRole(@PathVariable Long roleId) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(GET_ROLE).toUriString());
        return ResponseEntity.created(uri)
                .body(RoleConverter.convertToResponse(roleService.getRoleById(roleId)))
                .getBody();
    }

    @GetMapping(GET_ROLES)
    public List<RoleDto> getRoles() {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(GET_ROLES).toUriString());
        return ResponseEntity.created(uri)
                .body(RoleConverter.convertToResponse(roleService.getRoles()))
                .getBody();
    }

    @DeleteMapping(DELETE_ROLE)
    public void deleteRole(@PathVariable Long roleId) {
        roleService.deleteRoleById(roleId);
    }
}
