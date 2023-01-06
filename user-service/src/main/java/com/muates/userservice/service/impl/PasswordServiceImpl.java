package com.muates.userservice.service.impl;

import com.muates.commonservice.model.dto.NotificationDto;
import com.muates.userservice.model.dto.request.PasswordChangeRequest;
import com.muates.userservice.model.entity.User;
import com.muates.userservice.repository.UserRepository;
import com.muates.userservice.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class PasswordServiceImpl implements PasswordService {

    private final Logger log = LoggerFactory.getLogger(PasswordServiceImpl.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    @Override
    public String changePassword(Long userId, PasswordChangeRequest request) {
        Optional<User> optUser = userRepository.findById(userId);

        if (optUser.isEmpty()) {
            log.error("User does not exist, userId: {}", userId);
            throw new RuntimeException("User does not exist");
        }

        User user = optUser.get();

        if (!bCryptPasswordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            log.error("Password does not match");
            throw new RuntimeException("Password does not match");
        }

        String password = bCryptPasswordEncoder.encode(request.getPassword());
        user.setPassword(password);
        user.setUpdatedDate(new Date());
        userRepository.save(user);

        log.info("Password changed successfully");

        NotificationDto notification = createMessageRequest(user.getEmail());
        kafkaTemplate.send("user_notification", notification);
        return "Password changed successfully";
    }

    private NotificationDto createMessageRequest(String email) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setEmail(email);
        notificationDto.setSubject("Password changed");
        notificationDto.setText("Password changed successfully");
        return notificationDto;
    }
}
