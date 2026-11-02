// ui/CalculationFormPresenter.java
package ru.gr05507.ui;

import ru.gr05507.application.CalculationService;

public class CalculationFormPresenter {
    private final CalculatorView view;
    private final CalculationService service;

    public CalculationFormPresenter(CalculatorView view, CalculationService service) {
        this.view = view;
        this.service = service;
    }

    // Возвращает true, если расчёт выполнен и сохранён
    public boolean calculateClicked() {
        try {
            String formula = view.getFormula().trim();
            if (formula.isEmpty())
                throw new IllegalArgumentException("Введите формулу, например sin(x)");
            double lower = parseNumber(view.getLowerText(), "Нижний предел");
            double upper = parseNumber(view.getUpperText(), "Верхний предел");
            int intervals = parseInteger(view.getIntervalsText(), "Число отрезков");
            service.calculate(formula, lower, upper, intervals, view.getMethod());
            view.clearForm();
            return true;
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
            return false;
        }
    }

    private double parseNumber(String text, String fieldName) {
        if (text == null || text.isBlank())
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        try {
            // Запятая допускается как десятичный разделитель: 0,5 и 0.5 одинаковы
            return Double.parseDouble(text.trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + ": введите число");
        }
    }

    private int parseInteger(String text, String fieldName) {
        if (text == null || text.isBlank())
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + ": введите целое число");
        }
    }
}