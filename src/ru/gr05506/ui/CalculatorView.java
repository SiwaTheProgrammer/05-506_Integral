// ui/CalculatorView.java
package ru.gr05506.ui;

import ru.gr05506.domain.Calculation;
import ru.gr05506.domain.RealisationMethod;

import java.util.List;

// Контракт между презентерами и окном
public interface CalculatorView {
    String getFormula();
    String getLowerText();
    String getUpperText();
    String getIntervalsText();
    RealisationMethod getMethod();
    Calculation getSelected();

    void clearForm();
    void showCalculations(List<Calculation> calculations);
    void showError(String message);
    void drawGraph(Calculation calculation);
}