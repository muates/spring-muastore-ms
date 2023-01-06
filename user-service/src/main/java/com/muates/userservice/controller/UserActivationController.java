package com.muates.userservice.controller;

import com.muates.userservice.service.UserActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.muates.userservice.controller.endpoint.UserActivationEndpoint.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserActivationController {

    private final UserActivationService userActivationService;

    @GetMapping(ACTIVATE_USER)
    public String activateUser(@PathVariable String token) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(ACTIVATE_USER).toUriString());
        return ResponseEntity.created(uri).body(userActivationService.activateUser(token)).getBody();
    }
}
