package ru.gr05506.application;

import ru.gr05506.domain.Calculation;

public class DeleteCommand implements Command{
    private final CalculationService service;
    private final Calculation calculation;

    public DeleteCommand(
            CalculationService service,
            Calculation calculation
    ) {
        this.service = service;
        this.calculation = calculation;
    }

    @Override
    public void execute() {
        service.delete(calculation);
    }

    @Override
    public void undo() {
        service.restore(calculation);
    }
}
