package ru.gr05506.domain;

public record RealisationMethod(
        String name,
        CalculationMethod calculationMethod
) {
    @Override
    public String toString() {
        return name;
    }
}
