// ui/CalculationListPresenter.java
package ru.gr05507.ui;

import ru.gr05507.application.CalculationService;
import ru.gr05507.domain.Calculation;

public class CalculationListPresenter {
    private final CalculatorView view;
    private final CalculationService service;

    public CalculationListPresenter(CalculatorView view, CalculationService service) {
        this.view = view;
        this.service = service;
    }

    public void start() {
        refresh();
    }

    public void refresh() {
        view.showCalculations(service.getAll());
    }

    public void deleteSelected() {
        Calculation calculation = view.getSelected();
        if (calculation == null) {
            view.showError("Выберите расчёт в списке");
            return;
        }
        service.delete(calculation);
        refresh();
    }
}