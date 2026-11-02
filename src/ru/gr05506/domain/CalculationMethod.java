package ru.gr05506.domain;

import java.util.function.DoubleUnaryOperator;

public interface CalculationMethod {
    double calculation(DoubleUnaryOperator f, double a, double b, int n);
}
