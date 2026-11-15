// ui/CalculationFormPresenter.java
package ru.gr05506.ui;

import ru.gr05506.application.commands.CalculateCommand;
import ru.gr05506.application.CalculationService;
import ru.gr05506.application.commands.Command;
import ru.gr05506.application.commands.CommandHistory;

public class CalculationFormPresenter {
    private final CalculatorView view;
    private final CalculationService service;
    private final CommandHistory history;

    public CalculationFormPresenter(
            CalculatorView view,
            CalculationService service,
            CommandHistory history
    ) {
        this.view = view;
        this.service = service;
        this.history = history;
    }

    // Возвращает true, если расчёт выполнен и сохранён
    public void calculateClicked() {
        try {
            history.execute(buildCommand());
            view.clearForm();
        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
        }
    }

    public boolean canExecute() {
        try {
            return buildCommand().canExecute();
        } catch (IllegalArgumentException _) {
            return false;
        }
    }

    public void undoLast(){
        if (!history.undo()) {
            view.showError("Отменять нечего!");
        }
    }

    public boolean canUndo() {
        return history.canUndo();
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

    private CalculateCommand buildCommand() {
        String formula = view.getFormula().trim();
        if (formula.isEmpty())
            throw new IllegalArgumentException("Введите формулу, например sin(x)");
        double lower = parseNumber(view.getLowerText(), "Нижний предел");
        double upper = parseNumber(view.getUpperText(), "Верхний предел");
        int intervals = parseInteger(view.getIntervalsText(), "Число отрезков");
        return new CalculateCommand(service, formula, lower, upper, intervals, view.getMethod());
    }
}