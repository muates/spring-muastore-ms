package com.muates.userservice.scheduler.service.impl;

import com.muates.commonservice.model.dto.NotificationDto;
import com.muates.userservice.model.entity.User;
import com.muates.userservice.repository.UserRepository;
import com.muates.userservice.scheduler.service.UserRemoveService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserRemoveServiceImpl implements UserRemoveService {

    private final Logger log = LoggerFactory.getLogger(UserRemoveServiceImpl.class);

    private final UserRepository userRepository;
    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    @Override
    public void removeUser() {
        List<User> users = userRepository.findAllExpiredUserByCreatedDate();
        userRepository.deleteAll(users);

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setSubject("Your account removed");
        notificationDto.setText("User removed, because your didn't activate");

        for (var user : users) {
            notificationDto.setEmail(user.getEmail());
            kafkaTemplate.send("user_remove_notification", notificationDto);
            log.info("User removed, Id: {}, Email: {}", user.getId(), user.getEmail());
        }
    }
}
