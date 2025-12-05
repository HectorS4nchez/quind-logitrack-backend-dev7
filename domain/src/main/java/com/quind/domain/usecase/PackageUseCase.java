package com.quind.domain.usecase;

import com.quind.domain.port.repository.PackageRepository;

public class PackageUseCase {

    private final PackageRepository packageRepository;

    public PackageUseCase(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }
}
