package com.quind.domain.model;

public class DimensionsModel {

    private double height;
    private double width;
    private double depth;

    public DimensionsModel() {
    }

    public DimensionsModel(double height, double width, double depth) {
        validateDimension(height, "Height");
        validateDimension(width, "Width");
        validateDimension(depth, "Depth");

        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    private void validateDimension(double value, String dimensionName) {
        if (value <= 0) {
            throw new IllegalArgumentException(dimensionName + " must be greater than zero");
        }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        validateDimension(height, "Height");
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        validateDimension(width, "Width");
        this.width = width;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        validateDimension(depth, "Depth");
        this.depth = depth;
    }
}