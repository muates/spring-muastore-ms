package com.muates.userservice.controller;

import com.muates.userservice.service.UserActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserActivationController {

    private final UserActivationService userActivationService;

    @GetMapping("/api/user/v1/activation/{token}")
    public String activateUser(@PathVariable String token) {
        return userActivationService.activateUser(token);
    }
}
