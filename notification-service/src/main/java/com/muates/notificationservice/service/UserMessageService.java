package com.muates.notificationservice.service;

import com.muates.commonservice.model.dto.NotificationDto;

public interface UserMessageService {
    void sendEmail(NotificationDto request);
}
