package ru.gr05506.application;

import ru.gr05506.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class CalculationService {
    private final CalculationRepository repository;
    private final FormulaParser parser;
    private final List<CalculationsChangeListener> listeners = new ArrayList<>();

    public CalculationService(CalculationRepository repository, FormulaParser parser) {
        this.repository = repository;
        this.parser = parser;
    }

    // Разбирает формулу, вычисляет интеграл и сохраняет расчёт
    public Calculation calculate(String formula, double lower, double upper,
                                 int intervals, RealisationMethod method) {
        DoubleUnaryOperator f = parser.parse(formula);
        double result = Integrator.integrate(f, lower, upper, intervals, method);
        Calculation calculation = new Calculation(formula, lower, upper, intervals, method, result);
        repository.save(calculation);
        notifyListeners();
        return calculation;
    }

    public void delete(Calculation calculation) {
        repository.delete(calculation);
        notifyListeners();
    }

    public void restore(Calculation calculation) {
        repository.save(calculation);
        notifyListeners();
    }

    public List<Calculation> getAll() {
        return repository.findAll();
    }
    public void addChangeListener(CalculationsChangeListener listener) {
        listeners.add(listener);
    }


    private void notifyListeners(){
        for (var listener:  listeners) {
            listener.calculationsChanged();
        }
    }
}