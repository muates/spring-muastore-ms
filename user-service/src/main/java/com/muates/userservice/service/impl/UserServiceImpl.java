package com.muates.userservice.service.impl;

import com.muates.commonservice.model.dto.NotificationDto;
import com.muates.memberserviceclient.client.MemberServiceClient;
import com.muates.memberserviceclient.model.MemberCreateRequest;
import com.muates.userservice.converter.UserConverter;
import com.muates.userservice.model.dto.request.UserActivationRequest;
import com.muates.userservice.model.dto.request.UserRegistrationRequest;
import com.muates.userservice.model.entity.User;
import com.muates.userservice.repository.UserRepository;
import com.muates.userservice.service.UserActivationService;
import com.muates.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserActivationService userActivationService;
    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;
    private final MemberServiceClient memberServiceClient;

    @Override
    public User register(UserRegistrationRequest request) {
        User user = UserConverter.convertToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setEmail(user.getEmail());
        memberServiceClient.createMember(user.getId(), memberCreateRequest);

        UserActivationRequest userActivationRequest = createActivationRequest(user);

        String token = userActivationService.createToken(userActivationRequest);

        NotificationDto notification = createMessageRequest(request.getEmail(), token);

        kafkaTemplate.send("user_notification", notification);

        return user;
    }

    private NotificationDto createMessageRequest(String email, String token) {
        String text = "http://localhost:8081/api/user/v1/activation/" + token;
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setSubject("Confirm your account");
        notificationDto.setText(text);
        notificationDto.setEmail(email);
        return notificationDto;
    }

    private UserActivationRequest createActivationRequest(User user) {
        UserActivationRequest userActivationRequest = new UserActivationRequest();
        userActivationRequest.setEmail(user.getEmail());
        userActivationRequest.setUsername(user.getUsername());
        userActivationRequest.setUserId(user.getId());
        return userActivationRequest;
    }
}
