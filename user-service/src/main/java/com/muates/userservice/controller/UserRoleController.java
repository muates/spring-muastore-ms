package com.muates.userservice.controller;

import com.muates.userservice.model.dto.request.UserRoleRequest;
import com.muates.userservice.model.dto.response.UserRoleDto;
import com.muates.userservice.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.muates.userservice.controller.endpoint.UserRoleEndpoint.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping(ADD_ROLE_TO_USER)
    public UserRoleDto addRoleToUser(@RequestBody @Valid UserRoleRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(ADD_ROLE_TO_USER).toUriString());
        return ResponseEntity.created(uri)
                .body(userRoleService.addRoleToUser(request))
                .getBody();
    }

    @PostMapping(REMOVE_ROLE_FROM_USER)
    public UserRoleDto removeRoleFromUser(@RequestBody @Valid UserRoleRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(REMOVE_ROLE_FROM_USER).toUriString());
        return ResponseEntity.created(uri)
                .body(userRoleService.removeRoleFromUser(request))
                .getBody();
    }
}
