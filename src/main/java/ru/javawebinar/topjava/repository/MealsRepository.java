package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

public interface MealsRepository {

    boolean delete(int id);

    Meal getById(int id);

    void save (Meal meal);

}
