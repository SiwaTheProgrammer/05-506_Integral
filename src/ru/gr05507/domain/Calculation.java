package ru.gr05507.domain;

// Один расчёт: входные параметры и полученный ответ
public class Calculation {
    private final String formula;           // формула f(x), как её ввёл пользователь
    private final double lower;             // нижний предел a
    private final double upper;             // верхний предел b
    private final int intervals;            // число отрезков n
    private final IntegrationMethod method;
    private final double result;            // значение интеграла

    public Calculation(String formula, double lower, double upper,
                       int intervals, IntegrationMethod method, double result) {
        if (formula == null || formula.isBlank())
            throw new IllegalArgumentException("Формула не может быть пустой");
        if (method == null)
            throw new IllegalArgumentException("Метод обязателен");
        this.formula = formula.trim();
        this.lower = lower;
        this.upper = upper;
        this.intervals = intervals;
        this.method = method;
        this.result = result;
    }

    public String getFormula()            { return formula; }
    public double getLower()              { return lower; }
    public double getUpper()              { return upper; }
    public int getIntervals()             { return intervals; }
    public IntegrationMethod getMethod()  { return method; }
    public double getResult()             { return result; }
}