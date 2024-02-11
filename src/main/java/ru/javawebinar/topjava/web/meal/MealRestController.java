package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public MealTo get(int id, int userId) {
        Meal meal = service.get(id, userId);
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(),
                meal.getCalories(), false);
    }

    public Meal create(Meal meal) {
        return service.create(meal);
    }

    public void delete(int id, int userId) {
        service.delete(id, userId);
    }

    public void update(Meal meal) {
        service.update(meal);
    }
}
