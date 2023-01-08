package com.muates.userservice.service;

import com.muates.userservice.model.dto.request.PasswordChangeRequest;
import com.muates.userservice.model.dto.request.PasswordUpdateRequest;

public interface PasswordService {
    String changePassword(Long userId, PasswordChangeRequest request);
    String sendToken(String email);
    String updatePassword(PasswordUpdateRequest request);
}
