package ru.gr05506.domain;

public enum IntegrationMethod {
    RECTANGLES("Средних прямоугольников"),
    TRAPEZOIDS("Трапеций"),
    SIMPSON("Симпсона");

    private final String label;

    IntegrationMethod(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;                       // так название показывается в JComboBox
    }
}