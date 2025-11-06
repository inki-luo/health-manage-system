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
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (1, 1, '2025-10-22 22:41:00', 388);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (2, 2, '2025-10-23 21:04:00', 232);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (3, 3, '2025-10-24 11:12:00', 450);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (4, 4, '2025-10-25 05:33:00', 243);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (5, 5, '2025-10-26 04:29:00', 150);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (6, 6, '2025-10-27 18:57:00', 367);
INSERT IGNORE INTO exercise(id, type_id, date, kilocalories) VALUES (7, 3, '2025-10-28 23:26:00', 171);


INSERT IGNORE INTO food_names(food_name) VALUES ('Veggie ');
INSERT IGNORE INTO food_names(food_name) VALUES ('Fruit');
INSERT IGNORE INTO food_names(food_name) VALUES ('Milk/Yogurt/Cheese');
INSERT IGNORE INTO food_names(food_name) VALUES ('Protein foods');
INSERT IGNORE INTO food_names(food_name) VALUES ('Grain');

INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (1, 1, '2025-08-31 06:07:00', 18);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (2, 2, '2025-08-31 23:26:00', 102);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (3, 3, '2025-08-31 01:54:00', 79);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (4, 4, '2025-09-01 18:57:00', 38);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (5, 5, '2025-09-01 18:21:00', 50);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (1, 1, '2025-10-22 06:07:00', 1218);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (2, 2, '2025-10-23 23:26:00', 1802);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (3, 3, '2025-10-24 01:54:00', 1179);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (4, 4, '2025-10-25 18:57:00', 1838);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (5, 5, '2025-10-26 18:21:00', 1050);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (5, 5, '2025-10-27 18:21:00', 1250);
INSERT IGNORE INTO food(id, name_id, date, kilocalories) VALUES (5, 5, '2025-10-28 18:21:00', 950);
