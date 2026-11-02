package ru.gr05506.domain;

import java.util.List;

public interface CalculationRepository {
    void save(Calculation calculation);
    void delete(Calculation calculation);
    List<Calculation> findAll();
}