package com.muates.userservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.muates.commonservice.model.dto.NotificationDto;
import com.muates.userservice.model.dto.request.PasswordChangeRequest;
import com.muates.userservice.model.dto.request.PasswordUpdateRequest;
import com.muates.userservice.model.entity.PasswordToken;
import com.muates.userservice.model.entity.User;
import com.muates.userservice.repository.PasswordTokenRepository;
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
    private final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;
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

        String text = "Password changed successfully";
        String subject = "Password changed";
        NotificationDto notification = createMessageRequest(user.getEmail(), text, subject);
        kafkaTemplate.send("user_notification", notification);
        return "Password changed successfully";
    }

    @Override
    public String sendToken(String email) {
        Optional<User> optUser = userRepository.findUserByEmail(email);

        if (optUser.isEmpty()) {
            log.info("Email does not exist, email: {}", email);
            throw new RuntimeException("Email does not exist");
        }

        User user = optUser.get();

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withClaim("email", user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);

        PasswordToken passwordToken = new PasswordToken();
        passwordToken.setToken(token);
        passwordToken.setUserId(user.getId());
        passwordToken.setCreatedDate(new Date());
        passwordTokenRepository.save(passwordToken);

        String subject = "Change password";
        NotificationDto notification = createMessageRequest(user.getEmail(), token, subject);
        kafkaTemplate.send("user_notification", notification);

        return "Sent mail to user";
    }

    @Override
    public String updatePassword(PasswordUpdateRequest request) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(request.getToken());

        Long userId = decodedJWT.getClaim("userId").asLong();

        Optional<User> optUser = userRepository.findById(userId);

        if (optUser.isEmpty()) {
            log.error("User does not found! userId: {}", userId);
            throw new RuntimeException("User does not found");
        }

        User user = optUser.get();

        Optional<PasswordToken> optPasswordToken = passwordTokenRepository.findByUserId(userId);

        if (optPasswordToken.isEmpty()) {
            log.error("Token does not exist");
            throw new IllegalStateException("Token does not exist");
        }

        PasswordToken passwordToken = optPasswordToken.get();

        if (!passwordToken.getEnable()) {
            log.error("Token already use");
            throw new IllegalStateException("Token already use");
        }

        passwordToken.setEnable(false);
        passwordToken.setUpdatedDate(new Date());
        passwordTokenRepository.save(passwordToken);

        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setUpdatedDate(new Date());
        userRepository.save(user);

        log.info("Password changed successfully, username: {}", user.getUsername());
        return "Password changed successfully";
    }

    private NotificationDto createMessageRequest(String email, String text, String subject) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setEmail(email);
        notificationDto.setSubject(subject);
        notificationDto.setText(text);
        return notificationDto;
    }
}
