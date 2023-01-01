package com.muates.userservice.service;

import com.muates.userservice.model.dto.request.UserRegistrationRequest;
import com.muates.userservice.model.entity.User;

public interface UserService {
    User register(UserRegistrationRequest request);
}
