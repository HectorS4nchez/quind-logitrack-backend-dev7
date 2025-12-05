package com.quind.repository.adapter.jpa.entity;

import com.quind.domain.model.enums.PackageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "packages")
public class PackageEntity {

    @Id
    @Column(name = "tracking_id")
    private String trackingId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id", nullable = false)
    private RecipientEntity recipient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dimensions_id", nullable = false)
    private DimensionsEntity dimensions;

    @Column(nullable = false)
    private double weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageStatus status;

    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocationHistoryEntity> locationHistory = new ArrayList<>();

    public PackageEntity() {
    }

    public PackageEntity(String trackingId, RecipientEntity recipient, DimensionsEntity dimensions, double weight) {
        this.trackingId = trackingId;
        this.recipient = recipient;
        this.dimensions = dimensions;
        this.weight = weight;
        this.status = PackageStatus.PENDING;
        this.locationHistory = new ArrayList<>();
    }

    public void addLocationHistory(LocationHistoryEntity location) {
        locationHistory.add(location);
        location.setPackageEntity(this);
    }

    public void removeLocationHistory(LocationHistoryEntity location) {
        locationHistory.remove(location);
        location.setPackageEntity(null);
    }
}