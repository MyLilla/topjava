package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepository implements MealRepository {

    private final EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public JpaMealRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.em = entityManagerFactory.createEntityManager();
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.getUser() == null) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
        }
        em.merge(meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal mealToDelete = em.find(Meal.class, id);

        if (mealToDelete != null && mealToDelete.getUser().getId() == userId) {
            em.remove(mealToDelete);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {

        Meal meal = em.find(Meal.class, id);
        if (meal != null && meal.getUser().getId() == userId) {
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("from Meal m where m.user.id = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createQuery("from Meal m where m.user.id= :userId " +
                        "and m.dateTime > :startDateTime " +
                        "and m.dateTime < :endDateTime")
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .setParameter("userId", userId)
                .getResultList();
    }
}
