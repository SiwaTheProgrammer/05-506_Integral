// infrastructure/Exp4jFormulaParser.java
package ru.gr05506.infrastructure;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import ru.gr05506.domain.FormulaParser;

import java.util.function.DoubleUnaryOperator;

// Разбор формулы выполняет библиотека exp4j; переменная называется x
public class Exp4jFormulaParser implements FormulaParser {

    @Override
    public DoubleUnaryOperator parse(String formula) {
        Expression expression = buildExpression(formula);
        return x -> evaluateAt(expression, x);   // функция: подставляем x и вычисляем
    }

    private Expression buildExpression(String formula) {
        try {
            return new ExpressionBuilder(formula).variables("x").build();
        } catch (RuntimeException ex) {
            // Ошибки библиотеки переводим в IllegalArgumentException, который показывает окно
            throw new IllegalArgumentException("Не удалось разобрать формулу: " + ex.getMessage(), ex);
        }
    }

    private double evaluateAt(Expression expression, double x) {
        try {
            return expression.setVariable("x", x).evaluate();
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Не удалось вычислить формулу в точке x = " + x, ex);
        }
    }
}