DELETE
FROM user_role;
DELETE
FROM users;
DELETE
FROM meals;
DELETE
FROM user_meal;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('GUEST', 100002);

INSERT INTO meals (date_time, description, calories)
VALUES ('2020-01-30 10:00:00', 'Завтрак', 500),
       ('2020-01-30 13:00:00', 'Обед', 1000),
       ('2020-01-30 20:00:00', 'Ужин', 500),
       ('2020-01-31 00:00:00', 'Еда на граничное значение', 100),
       ('2020-01-31 10:00:00', 'Завтрак', 1000),
       ('2020-01-31 13:00:00', 'Обед', 500),
       ('2020-01-31 20:00:00', 'Ужин', 410),
       ('2015-06-01 14:00:00', 'Админ ланч', 510),
       ('2015-06-01 21:00:00', 'Админ ужин', 1500);

INSERT INTO user_meal (meal_id, user_id)
VALUES (100003, 100000),
       (100004, 100000),
       (100005, 100000),
       (100006, 100000),
       (100007, 100000),
       (100008, 100000),
       (100009, 100000),
       (100010, 100001),
       (100011, 100001);
