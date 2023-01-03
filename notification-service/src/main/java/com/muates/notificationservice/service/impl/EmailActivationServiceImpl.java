package com.muates.notificationservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.muates.notificationservice.model.dto.UserActivationRequest;
import com.muates.notificationservice.model.entity.EmailToken;
import com.muates.notificationservice.repository.EmailActivationRepository;
import com.muates.notificationservice.service.EmailActivationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Optional;


@RequiredArgsConstructor
@Transactional
@Service
public class EmailActivationServiceImpl implements EmailActivationService {

    private final Logger log = LoggerFactory.getLogger(EmailActivationServiceImpl.class);

    private final EmailActivationRepository emailActivationRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String FROM;

    @RabbitListener(queues = "${mua.rabbit.user-queue}")
    @Override
    public void sendEmail(UserActivationRequest request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String token = createToken(request);
            String text = "http://localhost:8081/api/user/v1/activation/" + token;

            helper.setFrom(FROM);
            helper.setTo(request.getEmail());
            helper.setSubject("Confirm your account");
            helper.setText(text);

            mailSender.send(mimeMessage);

            EmailToken emailToken = EmailToken.builder()
                    .token(token)
                    .userId(request.getUserId())
                    .enable(true)
                    .createdDate(new Date())
                    .build();

            emailActivationRepository.save(emailToken);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }

    @Override
    public void updateEnable(Long userId) {
        Optional<EmailToken> optEmailToken = emailActivationRepository.findByUserId(userId);

        if (optEmailToken.isEmpty())
            throw new RuntimeException();

        EmailToken emailToken = optEmailToken.get();
        emailToken.setEnable(false);
        emailToken.setUpdatedDate(new Date());

        emailActivationRepository.save(emailToken);
    }

    @Override
    public Boolean isEnable(Long userId) {
        Optional<EmailToken> optEmailToken = emailActivationRepository.findByUserId(userId);

        if (optEmailToken.isEmpty())
            throw new RuntimeException();

        EmailToken emailToken = optEmailToken.get();

        return emailToken.getEnable();
    }

    private String createToken(UserActivationRequest request) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        return JWT.create()
                .withSubject(request.getUsername())
                .withClaim("userId", request.getUserId())
                .withClaim("email", request.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);
    }
}
