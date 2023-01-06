package com.muates.userservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String password;
}
