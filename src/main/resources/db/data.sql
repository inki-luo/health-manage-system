INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Running');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Cycling');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Swimming');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Yoga');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Strength Training');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Tennis');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Workouts');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Walking');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Indoor Rowing');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Elliptical');
INSERT IGNORE INTO exercise_types(exercise_type_name) VALUES ('Core Training');

INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (1, 1, '2025-08-31 22:41:00', 88);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (2, 2, '2025-09-01 21:04:00', 32);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (3, 3, '2025-09-01 11:12:00', 50);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (4, 4, '2025-09-03 05:33:00', 43);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (5, 5, '2025-09-05 04:29:00', 50);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (6, 6, '2025-09-06 18:57:00', 67);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (7, 3, '2025-09-14 23:26:00', 71);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (8, 1, '2025-09-14 01:54:00', 133);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (9, 2, '2025-09-14 06:31:00', 40);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (10, 3, '2025-07-01 06:07:00', 50);


INSERT IGNORE INTO food_names(food_name) VALUES ('Salad');
INSERT IGNORE INTO food_names(food_name) VALUES ('Durian');
INSERT IGNORE INTO food_names(food_name) VALUES ('Onigiri');
INSERT IGNORE INTO food_names(food_name) VALUES ('Oatmeal');
INSERT IGNORE INTO food_names(food_name) VALUES ('Sandwich');

INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (1, 1, '2025-08-31 06:07:00', 18);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (2, 2, '2025-08-31 23:26:00', 102);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (3, 3, '2025-08-31 01:54:00', 79);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (4, 4, '2025-09-01 18:57:00', 38);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (5, 5, '2025-09-01 18:21:00', 50);
