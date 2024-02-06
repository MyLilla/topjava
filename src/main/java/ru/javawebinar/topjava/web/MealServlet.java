package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InmemoryMeals;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private final InmemoryMeals inmemoryMeals = new InmemoryMeals();
    private static final Logger log = getLogger(MealServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        log.debug("action is: {}", action);

        if (action == null) {
            List<Meal> mealsList = inmemoryMeals.getMeals().entrySet().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            List<MealTo> meals = MealsUtil.filteredByStreams(mealsList,
                    LocalDateTime.MIN.toLocalTime(), LocalDateTime.MAX.toLocalTime(), MealsUtil.CALORIES_PER_DAY);

            log.debug("redirect to meals");
            request.setAttribute("meals", meals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else {

            String id = request.getParameter("id");
            switch (Objects.requireNonNull(action)) {
                case "delete": {
                    log.debug("delete meal with id: {}", id);
                    inmemoryMeals.delete(Integer.parseInt(id));
                    response.sendRedirect("/topjava_war_exploded/meals");
                    break;
                }
                case "edit": {
                    log.debug("edit meal with id: {}", id);
                    Meal meal = inmemoryMeals.getById(Integer.parseInt(id));
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("/create.jsp").forward(request, response);
                    break;
                }
                case "create": {
                    log.debug("Create meal");
                    LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
                    String description = request.getParameter("description");
                    int calories = Integer.parseInt(request.getParameter("calories"));

                    Meal newMeal = new Meal(date, description, calories);
                    if (!id.isEmpty()) {
                        newMeal.setId(Integer.parseInt(id));
                    }
                    inmemoryMeals.save(newMeal);
                    response.sendRedirect("/topjava_war_exploded/meals");
                    break;
                }

            }
        }
    }
}
