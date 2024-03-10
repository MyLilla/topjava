package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    MealService mealService;

    @GetMapping("/meals")
    public String getMeals(Model model) {

        model.addAttribute("meals", getAllMealsTo());
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(@Param("id") String id, Model model) {
        log.info("Action is DELETE");

        mealService.delete(Integer.parseInt(id), SecurityUtil.authUserId());
        model.addAttribute("meals", getAllMealsTo());
        return "meals";
    }

    @GetMapping("/update")
    public String update(@Param("id") String id, Model model) {
        log.info("Action is UPDATE");

        Meal meal = mealService.get(Integer.parseInt(id), SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String create(Model model) {

        log.info("Action is CREATE");
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return ("/mealForm");
    }

    @GetMapping("/filter")
    public String filter(Model model, @Param("startDate") String startDate,
                         @Param("endDate") String endDate,
                         @Param("startTime") String startTime,
                         @Param("endTime") String endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        LocalDate startDateLD = parseLocalDate(startDate);
        LocalDate endDateLD = parseLocalDate(endDate);
        LocalTime startTimeLD = parseLocalTime(startTime);
        LocalTime endTimeLD = parseLocalTime(endTime);

        List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(startDateLD, endDateLD, userId);
        List<MealTo> result = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTimeLD, endTimeLD);

        model.addAttribute("meals", result);
        return "/meals";
    }

    @PostMapping("/meals")
    public String update(Model model, @Param("dateTime") String dateTime,
                         @Param("description") String description,
                         @Param("calories") String calories,
                         @Param("id") String id) {

        Meal meal = new Meal(
                LocalDateTime.parse(dateTime), description,
                Integer.parseInt(calories));

        if (StringUtils.hasLength(id)) {
            mealService.update(meal, SecurityUtil.authUserId());
        } else {
            mealService.create(meal, SecurityUtil.authUserId());
        }
        model.addAttribute("meals", getAllMealsTo());
        return "meals";
    }

    private List<MealTo> getAllMealsTo() {
        List<Meal> meals = mealService.getAll(SecurityUtil.authUserId());
        return MealsUtil.getTos(meals, SecurityUtil.authUserCaloriesPerDay());
    }
}
