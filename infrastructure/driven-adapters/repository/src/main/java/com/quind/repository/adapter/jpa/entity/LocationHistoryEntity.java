package com.quind.repository.adapter.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "location_history")
public class LocationHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_tracking_id")
    private PackageEntity packageEntity;

    public LocationHistoryEntity() {
    }

    public LocationHistoryEntity(String city, String country, LocalDateTime timestamp) {
        this.city = city;
        this.country = country;
        this.timestamp = timestamp;
    }

}