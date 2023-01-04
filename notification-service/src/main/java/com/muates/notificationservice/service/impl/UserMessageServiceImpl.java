package com.muates.notificationservice.service.impl;

import com.muates.commonservice.model.dto.NotificationDto;
import com.muates.notificationservice.service.UserMessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Transactional
@Service
public class UserMessageServiceImpl implements UserMessageService {

    private final Logger log = LoggerFactory.getLogger(UserMessageServiceImpl.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String FROM;

    @KafkaListener(
            topics = "user_remove_notification",
            groupId = "group_id",
            containerFactory = "userListenerFactory"
    )
    @Override
    public void sendEmail(NotificationDto request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");

            helper.setFrom(FROM);
            helper.setTo(request.getEmail());
            helper.setSubject(request.getSubject());
            helper.setText(request.getText());

            mailSender.send(mimeMessage);

            log.info("Sent mail for user. Message subject: {}, User email: {}", request.getSubject(), request.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send email:", e);
            throw new IllegalStateException("Failed to send email");
        }
    }
}
