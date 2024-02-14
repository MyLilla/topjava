package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int userId;

    public static int authUserId() {
       return userId;
    }

    public static void setAuthUserId(int role) {
        switch (role){
            case 1: userId = 1;
            case 2: userId = 2;
            default: userId = 1;
        }
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}