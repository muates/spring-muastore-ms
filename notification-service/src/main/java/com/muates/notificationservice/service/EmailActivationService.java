package com.muates.notificationservice.service;

import com.muates.notificationservice.model.dto.UserActivationRequest;

public interface EmailActivationService {
    void sendEmail(UserActivationRequest request);
    void updateEnable(Long userId);
    Boolean isEnable(Long userId);
}
