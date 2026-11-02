package ru.gr05506.infrastructure;

import ru.gr05506.domain.Calculation;
import ru.gr05506.domain.CalculationRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCalculationRepository implements CalculationRepository {
    private final List<Calculation> calculations = new ArrayList<>();

    @Override
    public void save(Calculation calculation) {
        calculations.add(calculation);
    }

    @Override
    public void delete(Calculation calculation) {
        calculations.remove(calculation);   // Calculation сравнивается по ссылке
    }

    @Override
    public List<Calculation> findAll() {
        return List.copyOf(calculations);   // копия: внешний код не меняет хранилище
    }
}