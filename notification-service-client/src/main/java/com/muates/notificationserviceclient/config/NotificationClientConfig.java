package com.muates.notificationserviceclient.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@EnableAutoConfiguration
@EnableFeignClients(
        basePackages = {
                "com.muates.notificationserviceclient.client",
                "com.muates.notificationserviceclient.model"
        }
)
@Configuration
public class NotificationClientConfig {
}
