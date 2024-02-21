package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService mealService;


    @Test
    public void get() {
        Meal actual = mealService.get(MEAL_USER_ID, USER_ID);
        Meal expected = new Meal(MEAL_USER_ID, LocalDateTime.now(), "Ужин", 500);
        assertMatch(actual, expected);

    }

    @Test
    public void getNotBelongUser() {
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_USER_ID, ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(NOT_FOUND, NOT_FOUND));
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_USER_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_USER_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
    }

    @Test
    public void getAll() {
        assertEquals(COUNT_OF_MEALS_FOR_ADMIN, mealService.getAll(ADMIN_ID).size());
    }

    @Test
    public void getAllNotBelongAdmin() {
        assertNotEquals(COUNT_OF_MEALS_FOR_ADMIN, mealService.getAll(USER_ID).size());
    }

    @Test
    public void update() {
        Meal newMeal = new Meal(100005, LocalDateTime.now(), "Ужин", 600);
        mealService.update(newMeal, USER_ID);
        assertMatch(mealService.get(100005, USER_ID), newMeal);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(100005, LocalDateTime.now(), "Ужин", 600);
        Meal actual = mealService.create(newMeal, ADMIN_ID);
        assertMatch(actual, newMeal);
    }
}