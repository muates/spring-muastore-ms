package com.muates.userservice.controller;

import com.muates.userservice.model.dto.request.PasswordChangeRequest;
import com.muates.userservice.model.dto.request.PasswordUpdateRequest;
import com.muates.userservice.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;

import static com.muates.userservice.controller.endpoint.PasswordEndpoint.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class PasswordController {

    private final PasswordService passwordService;

    @PutMapping(CHANGE_PASSWORD)
    public String changePassword(@PathVariable Long userId,
                                 @RequestBody @Valid PasswordChangeRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(CHANGE_PASSWORD).toUriString());
        return ResponseEntity.created(uri).body(passwordService.changePassword(userId, request)).getBody();
    }

    @PostMapping(SEND_TOKEN)
    public String sendToken(@PathVariable String email) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(SEND_TOKEN).toUriString());
        return ResponseEntity.created(uri).body(passwordService.sendToken(email)).getBody();
    }

    @PutMapping(UPDATE_PASSWORD)
    public String updatePassword(@RequestBody @Valid PasswordUpdateRequest request) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(UPDATE_PASSWORD).toUriString());
        return ResponseEntity.created(uri).body(passwordService.updatePassword(request)).getBody();
    }
}
