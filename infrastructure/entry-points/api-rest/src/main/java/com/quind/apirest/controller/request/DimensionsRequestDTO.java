package com.quind.apirest.controller.request;

import jakarta.validation.constraints.Positive;

public class DimensionsRequestDTO {

    @Positive(message = "Height must be greater than zero")
    private double height;

    @Positive(message = "Width must be greater than zero")
    private double width;

    @Positive(message = "Depth must be greater than zero")
    private double depth;

    public DimensionsRequestDTO() {
    }

    public DimensionsRequestDTO(double height, double width, double depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }
}
