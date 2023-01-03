package com.muates.notificationserviceclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "notification-service")
public interface EmailActivationServiceClient {

    @GetMapping("/api/email/v1/enable/{userId}")
    Boolean isEnable(@PathVariable Long userId);

    @PutMapping("/api/email/v1/update/enable/{userId}")
    void updateEnable(@PathVariable Long userId);
}
