package com.quind.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.quind.apirest.controller",
        "com.quind.apirest.client"
})
public class WebConfig {
}