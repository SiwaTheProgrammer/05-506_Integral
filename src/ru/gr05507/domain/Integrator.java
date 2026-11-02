package ru.gr05507.domain;

import java.util.function.DoubleUnaryOperator;

// Численное вычисление определённого интеграла на отрезке [a; b] разбиением на n отрезков
public final class Integrator {

    private Integrator() { }                // утилитарный класс: экземпляры не создаём

    public static double integrate(DoubleUnaryOperator f, double a, double b,
                                   int n, IntegrationMethod method) {
        if (!(a < b))
            throw new IllegalArgumentException("Нижний предел должен быть меньше верхнего");
        if (n < 1)
            throw new IllegalArgumentException("Число отрезков должно быть не меньше 1");

        double h = (b - a) / n;                 // длина одного отрезка разбиения
        double sum = 0;

        switch (method) {
            case RECTANGLES -> {
                // Значение функции в середине каждого отрезка
                for (int i = 0; i < n; i++) {
                    sum += f.applyAsDouble(a + (i + 0.5) * h);
                }
                sum *= h;
            }
            case TRAPEZOIDS -> {
                // Концы отрезка входят с весом 1/2, внутренние узлы с весом 1
                sum = (f.applyAsDouble(a) + f.applyAsDouble(b)) / 2;
                for (int i = 1; i < n; i++) {
                    sum += f.applyAsDouble(a + i * h);
                }
                sum *= h;
            }
            case SIMPSON -> {
                if (n % 2 != 0)
                    throw new IllegalArgumentException("Для метода Симпсона число отрезков должно быть чётным");
                // Узлы с нечётным номером весят 4, внутренние с чётным номером весят 2
                sum = f.applyAsDouble(a) + f.applyAsDouble(b);
                for (int i = 1; i < n; i++) {
                    double weight = (i % 2 == 1) ? 4 : 2;
                    sum += weight * f.applyAsDouble(a + i * h);
                }
                sum *= h / 3;
            }
        }

        // Разрыв или неопределённость функции на отрезке дают NaN или бесконечность
        if (!Double.isFinite(sum))
            throw new IllegalArgumentException("Функция не определена или не ограничена на отрезке");
        return sum;
    }
}