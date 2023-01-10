package com.muates.memberserviceclient.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@EnableAutoConfiguration
@EnableFeignClients(basePackages = {
        "com.muates.memberserviceclient.client",
        "com.muates.memberserviceclient.model"
})
@Configuration
public class MemberServiceClientConfig {
}
