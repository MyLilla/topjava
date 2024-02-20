DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meals;
DELETE FROM user_meal;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories)
VALUES ('2024-02-19 10:00:00', 'Breakfast User', 55),
       ('2024-02-19 13:23:24', 'Some food User', 10),
       ('2024-02-19 15:55:14', 'Lunch User', 155),
       ('2024-02-19 15:55:09', 'Over User', 2000),
       ('2024-02-20 19:23:24', 'Some food Admin', 2200);

INSERT INTO user_meal (meal_id, user_id)
VALUES (100003, 100000),
       (100004, 100000),
       (100005, 100000),
       (100006, 100000),
       (100007, 100001);
