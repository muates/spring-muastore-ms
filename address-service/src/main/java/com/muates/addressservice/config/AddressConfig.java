package com.muates.addressservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.muates.addressservice")
@ComponentScan("com.muates")
public class AddressConfig {
}
