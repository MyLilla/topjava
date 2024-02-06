package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InmemoryMeals implements MealsRepository {

    private final AtomicInteger counter = new AtomicInteger();

    private ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    public InmemoryMeals() {

        Meal meal = new Meal(generateId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        Meal meal1 = new Meal(generateId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        Meal meal2 = new Meal(generateId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        Meal meal3 = new Meal(generateId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        Meal meal4 = new Meal(generateId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        Meal meal5 = new Meal(generateId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        Meal meal6 = new Meal(generateId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
        meals.put(meal.getId(), meal);
        meals.put(meal1.getId(), meal1);
        meals.put(meal2.getId(), meal2);
        meals.put(meal3.getId(), meal3);
        meals.put(meal4.getId(), meal4);
        meals.put(meal5.getId(), meal5);
        meals.put(meal6.getId(), meal6);

    }

    private int generateId() {
        return counter.incrementAndGet();
    }

    public Map<Integer, Meal> getMeals() {
        return meals;
    }

    @Override
    public boolean delete(int id) {
        return meals.remove(id) != null;
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    @Override
    public void save(Meal meal) {
        if (!meals.containsKey(meal.getId())) {
            meal.setId(generateId());
            meals.put(meal.getId(), meal);
        } else {
            meals.remove(meal.getId());
            meals.put(meal.getId(), meal);
        }
    }
}
