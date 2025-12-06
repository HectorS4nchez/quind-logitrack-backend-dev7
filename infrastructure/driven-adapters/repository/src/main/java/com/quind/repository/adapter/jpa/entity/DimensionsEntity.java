package com.quind.repository.adapter.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "dimensions")
public class DimensionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private double width;

    @Column(nullable = false)
    private double depth;

    public DimensionsEntity() {
    }

    public DimensionsEntity(double height, double width, double depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

}