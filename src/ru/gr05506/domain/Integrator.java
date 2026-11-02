package ru.gr05506.domain;

import java.util.function.DoubleUnaryOperator;

// Численное вычисление определённого интеграла на отрезке [a; b] разбиением на n отрезков
public final class Integrator {

    private Integrator() { }                // утилитарный класс: экземпляры не создаём

    public static double integrate(DoubleUnaryOperator f, double a, double b,
                                   int n, RealisationMethod method) {
        if (!(a < b))
            throw new IllegalArgumentException("Нижний предел должен быть меньше верхнего");
        if (n < 1)
            throw new IllegalArgumentException("Число отрезков должно быть не меньше 1");

        double sum = method.calculationMethod().calculation(f,a,b,n);

        // Разрыв или неопределённость функции на отрезке дают NaN или бесконечность
        if (!Double.isFinite(sum))
            throw new IllegalArgumentException("Функция не определена или не ограничена на отрезке");
        return sum;
    }
}