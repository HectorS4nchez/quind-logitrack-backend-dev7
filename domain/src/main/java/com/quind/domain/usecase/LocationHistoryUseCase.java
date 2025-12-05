package com.quind.domain.usecase;

import com.quind.domain.port.repository.LocationHistoryRepository;

public class LocationHistoryUseCase {

    private final LocationHistoryRepository locationHistoryRepository;

    public LocationHistoryUseCase(LocationHistoryRepository locationHistoryRepository) {
        this.locationHistoryRepository = locationHistoryRepository;
    }
}
