package com.quind.application.config;

import com.quind.domain.port.repository.*;
import com.quind.domain.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public DimensionsUseCase dimensionsUseCase(DimensionsRepository repository) {
        return new DimensionsUseCase(repository);
    }

    @Bean
    public LocationHistoryUseCase locationHistoryUseCase(LocationHistoryRepository repository) {
        return new LocationHistoryUseCase(repository);
    }

    @Bean
    public PackageUseCase packageUseCase(PackageRepository repository) {
        return new PackageUseCase(repository);
    }

    @Bean
    public RecipientUseCase recipientUseCase(RecipientRepository repository) {
        return new RecipientUseCase(repository);
    }
}
