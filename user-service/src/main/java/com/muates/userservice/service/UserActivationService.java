package com.muates.userservice.service;

import com.muates.userservice.model.dto.request.UserActivationRequest;

public interface UserActivationService {
    String activateUser(String token);
    String createToken(UserActivationRequest request);
}
