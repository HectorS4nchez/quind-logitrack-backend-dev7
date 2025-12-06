package com.quind.domain.validations;

import com.quind.domain.model.enums.PackageStatus;

import java.util.*;

public class PackageStateValidator {

    private static final Map<PackageStatus, Set<PackageStatus>> VALID_TRANSITIONS = new HashMap<>();

    static {
        VALID_TRANSITIONS.put(PackageStatus.CREATED, Set.of(
                PackageStatus.IN_TRANSIT
        ));

        VALID_TRANSITIONS.put(PackageStatus.IN_TRANSIT, Set.of(
                PackageStatus.OUT_FOR_DELIVERY,
                PackageStatus.DELIVERY_FAILED,
                PackageStatus.RETURNED
        ));

        VALID_TRANSITIONS.put(PackageStatus.OUT_FOR_DELIVERY, Set.of(
                PackageStatus.DELIVERED,
                PackageStatus.DELIVERY_FAILED
        ));

        VALID_TRANSITIONS.put(PackageStatus.DELIVERED, Set.of());

        VALID_TRANSITIONS.put(PackageStatus.DELIVERY_FAILED, Set.of(
                PackageStatus.IN_TRANSIT,
                PackageStatus.RETURNED
        ));

        VALID_TRANSITIONS.put(PackageStatus.RETURNED, Set.of());
    }

    public static boolean isValidTransition(PackageStatus currentStatus, PackageStatus newStatus) {
        if (currentStatus == null || newStatus == null) {
            return false;
        }
        Set<PackageStatus> allowedTransitions = VALID_TRANSITIONS.get(currentStatus);
        return allowedTransitions != null && allowedTransitions.contains(newStatus);
    }

    public static void validateTransition(PackageStatus currentStatus, PackageStatus newStatus) {
        if (!isValidTransition(currentStatus, newStatus)) {
            throw new IllegalStateException(
                    String.format("Invalid state transition from %s to %s", currentStatus, newStatus)
            );
        }
    }

    public static Set<PackageStatus> getValidTransitions(PackageStatus currentStatus) {
        return VALID_TRANSITIONS.getOrDefault(currentStatus, Set.of());
    }
}