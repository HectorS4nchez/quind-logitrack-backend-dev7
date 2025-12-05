package com.quind.application.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.quind.repository.adapter")
@EntityScan(basePackages = "com.quind.repository.adapter.jpa.entity")
@EnableJpaRepositories(basePackages = "com.quind.repository.adapter.jpa.repository")
public class RepositoryConfig {
}
