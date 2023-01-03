package com.muates.notificationservice.controller;

import com.muates.notificationservice.service.EmailActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.muates.notificationservice.controller.endpoint.EmailActivationControllerEndpoint.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class EmailActivationController {

    private final EmailActivationService emailActivationService;

    @PutMapping(UPDATE_ENABLE)
    public void updateEnable(@PathVariable Long userId) {
        emailActivationService.updateEnable(userId);
    }

    @GetMapping(IS_ENABLE)
    public Boolean isEnable(@PathVariable Long userId) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(IS_ENABLE).toUriString());
        return ResponseEntity.created(uri).body(emailActivationService.isEnable(userId)).getBody();

    }
}
