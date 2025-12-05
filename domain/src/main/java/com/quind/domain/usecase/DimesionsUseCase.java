package com.quind.domain.usecase;

import com.quind.domain.port.repository.DimesionsRepository;

public class DimesionsUseCase {

    private final DimesionsRepository dimesionsRepository;

    public DimesionsUseCase(DimesionsRepository dimesionsRepository) {
        this.dimesionsRepository = dimesionsRepository;
    }
}
