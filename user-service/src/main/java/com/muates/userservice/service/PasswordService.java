package com.muates.userservice.service;

import com.muates.userservice.model.dto.request.PasswordChangeRequest;

public interface PasswordService {
    String changePassword(Long userId, PasswordChangeRequest request);
}
