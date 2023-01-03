package com.muates.userservice.service.impl;

import com.muates.userservice.converter.UserConverter;
import com.muates.userservice.model.dto.request.UserActivationRequest;
import com.muates.userservice.model.dto.request.UserRegistrationRequest;
import com.muates.userservice.model.entity.User;
import com.muates.userservice.repository.UserRepository;
import com.muates.userservice.service.UserService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmqpTemplate amqpTemplate;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           @Qualifier("template") AmqpTemplate amqpTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public User register(UserRegistrationRequest request) {
        User user = UserConverter.convertToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        UserActivationRequest userActivationRequest = new UserActivationRequest();
        userActivationRequest.setEmail(user.getEmail());
        userActivationRequest.setUsername(user.getUsername());
        userActivationRequest.setUserId(user.getId());

        sendActivationEmail(userActivationRequest);

        return user;
    }

    private void sendActivationEmail(UserActivationRequest userActivationRequest) {
        amqpTemplate.convertAndSend("exchange_mua", "user-route", userActivationRequest);
    }
}
