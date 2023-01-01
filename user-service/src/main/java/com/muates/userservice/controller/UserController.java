package com.muates.userservice.controller;

import com.muates.userservice.converter.UserConverter;
import com.muates.userservice.model.dto.request.UserRegistrationRequest;
import com.muates.userservice.model.dto.response.UserDto;
import com.muates.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.muates.userservice.controller.endpoint.UserEndpoint.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @PostMapping(REGISTER)
    public UserDto register(@RequestBody @Valid UserRegistrationRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(REGISTER).toUriString());
        return ResponseEntity.created(uri).body(UserConverter.covertToResponse(userService.register(request))).getBody();
    }
}
