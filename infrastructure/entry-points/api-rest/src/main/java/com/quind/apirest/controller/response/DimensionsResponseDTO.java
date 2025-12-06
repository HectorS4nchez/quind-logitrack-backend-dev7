package com.quind.apirest.controller.response;

/**
 * DTO para respuestas de dimensiones. [web:21]
 */
public class DimensionsResponseDTO {

    private Long id;
    private double height;
    private double width;
    private double depth;

    public DimensionsResponseDTO() {
    }

    public DimensionsResponseDTO(Long id, double height, double width, double depth) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
