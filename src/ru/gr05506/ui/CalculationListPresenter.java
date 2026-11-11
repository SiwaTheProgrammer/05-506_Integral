// ui/CalculationListPresenter.java
package ru.gr05506.ui;

import ru.gr05506.application.CalculationService;
import ru.gr05506.application.CalculationsChangeListener;
import ru.gr05506.application.commands.CommandHistory;
import ru.gr05506.application.commands.DeleteCommand;
import ru.gr05506.domain.Calculation;

public class CalculationListPresenter implements CalculationsChangeListener {
    private final CalculatorView view;
    private final CalculationService service;
    private final CommandHistory history;

    public CalculationListPresenter(
            CalculatorView view,
            CalculationService service,
            CommandHistory history
    ) {
        this.view = view;
        this.service = service;
        this.history = history;
    }

    public void start() {
        service.addChangeListener(this);
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
        //service.delete(calculation);
        history.execute(new DeleteCommand(service, calculation));
    }

    @Override
    public void calculationsChanged() {
        refresh();
    }
}