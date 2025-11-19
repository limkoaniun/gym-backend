DELETE FROM muscle;
DELETE FROM equipment;
DELETE FROM equipment_tag;
DELETE FROM equipment_usage_muscles;
DELETE FROM equipment_usage;
DELETE FROM user;
DELETE FROM media;

INSERT  INTO user(email, first_name, last_name, password, role, username)
VALUES
    ('admin@email.com','admin','User','admin123','admin','admin'),
    ('customer@email.com','customer','User','customer123','customer','customer');

INSERT INTO equipment (name, description)
VALUES
    ('Barbell Bench Press', 'Standard bench press'),
    ('Dumbbells', 'Free weight dumbbells');

INSERT INTO equipment_usage (name, description, equipment_id)
VALUES
    ('Flat Bench Press', 'Chest strength training', 1),
    ('Incline Bench Press', 'Upper chest focus', 1),
    ('Decline Bench Press', 'Lower chest focus', 1),
    ('Dumbbell Bench Press', 'Dumbbell chest training', 2),
    ('Close-Grip Bench Press', 'Triceps focus version', 1);

INSERT INTO media(name, original_file_name, type, url)
VALUES
    ('Bench Press','bench_press.png','IMAGE',''),
    ('Bench Setup Step','bench_setup_step_1.png','IMAGE',''),
    ('Dumbbells Set','dumbbells_set.png','IMAGE',''),
    ('Barbell Bench Press','barbell_bench_press.png','IMAGE',''),
    ('Bench Press Tutorial','bench_press_tutorial.mp4','VIDEO','');

INSERT INTO equipment_tag(name)
VALUES ('CARDIO'),('STRENGTH'),('HOT'),('POPULAR');

INSERT INTO muscle (name)
VALUES ('CHEST'),
       ('UPPER_CHEST'),
       ('LOWER_CHEST'),
       ('BACK'),
       ('UPPER_BACK'),
       ('LOWER_BACK'),
       ('LATS'),
       ('TRAPS'),
       ('SHOULDERS'),
       ('FRONT_DELTS'),
       ('SIDE_DELTS'),
       ('REAR_DELTS'),
       ('BICEPS'),
       ('TRICEPS'),
       ('FOREARMS'),

       ('ABS'),
       ('OBLIQUES'),
       ('LOWER_ABS'),
       ('UPPER_ABS'),

       ('QUADS'),
       ('HAMSTRINGS'),
       ('GLUTES'),
       ('CALVES'),
       ('ADDUCTORS'),
       ('ABDUCTORS');

INSERT INTO equipment_usage_muscles (equipment_usage_id, muscles_id)
VALUES (1, 1), (1, 10), (2, 2), (3, 1);


