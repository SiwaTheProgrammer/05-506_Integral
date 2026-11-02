package ru.gr05507.domain;

import java.util.List;

public interface CalculationRepository {
    void save(Calculation calculation);
    void delete(Calculation calculation);
    List<Calculation> findAll();
}