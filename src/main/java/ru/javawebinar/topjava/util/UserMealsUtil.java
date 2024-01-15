package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0),
                LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime,
                                                            int caloriesPerDay) {
        Map<LocalDate, Integer> daysWithExcess = getExcessDays(meals);

        List<UserMealWithExcess> mealWithExcesses = new ArrayList<>();
        for (int i = 0; i < meals.size() - 1; i++) {
            UserMeal userMeal = meals.get(i);
            UserMealWithExcess exMeal = new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                    userMeal.getCalories(),
                    (daysWithExcess.get(userMeal.getDateTime().toLocalDate()) >= caloriesPerDay));

            mealWithExcesses.add(exMeal);
        }
        List<UserMealWithExcess> result = new ArrayList<>();

        for (int i = 0; i < mealWithExcesses.size() - 1; i++) {
            if (TimeUtil.isBetweenHalfOpen(mealWithExcesses.get(i).getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(mealWithExcesses.get(i));
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> daysWithExcess = getExcessDays(meals);

        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(),
                        startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(), (daysWithExcess.get(userMeal.getDateTime().toLocalDate()) >= caloriesPerDay)))
                .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getExcessDays(List<UserMeal> meals) {

        return meals.stream()
                .collect(Collectors.toMap(userMeal -> userMeal.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        Integer::sum));

//        Map<LocalDate, Integer> daysWithExcess = new HashMap<>();
//        for (int i = 0; i < meals.size() - 1; i++) {
//            LocalDate date = meals.get(i).getDateTime().toLocalDate();
//
//            if (daysWithExcess.containsKey(date)) {
//                daysWithExcess.put(date, meals.get(i).getCalories() + daysWithExcess.get(date));
//            } else {
//                daysWithExcess.put(date, meals.get(i).getCalories());
//            }
//        }
    }
}
