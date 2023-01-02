package com.muates.notificationservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.muates.notificationservice.model.dto.UserActivationRequest;
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

@RequiredArgsConstructor
@Transactional
@Service
public class EmailActivationServiceImpl implements EmailActivationService {

    private final Logger log = LoggerFactory.getLogger(EmailActivationServiceImpl.class);

    private final EmailActivationRepository emailActivationRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private final String FROM;

    @RabbitListener(queues = "${mua.rabbit.user-queue}")
    @Override
    public void sendEmail(UserActivationRequest request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String token = createToken(request);
            String text = "http://localhost:8081/api/user/activation?token=" + token;

            helper.setFrom(FROM);
            helper.setTo(request.getEmail());
            helper.setSubject("Confirm your account");
            helper.setText(text);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }

    private String createToken(UserActivationRequest request) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        return JWT.create()
                .withSubject(request.getUsername())
                .withClaim("userId", request.getUserId())
                .withClaim("email", request.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .sign(algorithm);
    }
}
