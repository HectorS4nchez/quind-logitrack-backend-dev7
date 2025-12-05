package com.quind.domain.usecase;

import com.quind.domain.port.repository.LocalizationHistoryRepository;

public class LocationHistoryUseCase {

    private final LocalizationHistoryRepository locationHistoryRepository;

    public LocationHistoryUseCase(LocalizationHistoryRepository locationHistoryRepository) {
        this.locationHistoryRepository = locationHistoryRepository;
    }
}
