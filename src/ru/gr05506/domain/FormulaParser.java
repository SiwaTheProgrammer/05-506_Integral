// domain/FormulaParser.java
package ru.gr05506.domain;

import java.util.function.DoubleUnaryOperator;

// Превращает текст формулы в функцию одной переменной x.
// При ошибке разбора бросает IllegalArgumentException
public interface FormulaParser {
    DoubleUnaryOperator parse(String formula);
}