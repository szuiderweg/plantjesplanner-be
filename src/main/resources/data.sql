
-- 2. Data inserts

-- Insert Locales
INSERT INTO locales (id, sunlight, moisture, wind_tolerance, soil_type, open_ground_only)
VALUES
    (1, 'HALFSCHADUW', 'DROOG', 'BESCHUT', 'tuinaarde, bosgrond', true),
    (2, 'ZONNIG', 'MATIG_VOCHTIG', 'BESCHUT', 'klei, zandgrond', false),
    (3, 'SCHADUW', 'VOCHTIG', 'STERKE_WIND', 'veen, humusgrond', true);

-- Insert BloomingCalendars
INSERT INTO blooming_calendars (id, january, february, march, april, may, june, july, august, september, october, november, december)
VALUES
    (1, false, false, false, true, true, true, true, true, true, false, false, false),
    (2, false, false, true, true, true, false, false, false, false, false, false, false),
    (3, false, false, false, false, true, true, true, false, false, false, false, false);

-- Insert Images
INSERT INTO images (id, original_filename, stored_filename, upload_date_time)
VALUES (1, 'klimroos.jpg', 'klimroos.jpg', '2025-04-28T20:45:00+02:00'),
       (2, 'zonnebloem.jpg', 'zonnebloem.jpg', '2025-04-28T20:45:00+02:00'),
       (3, 'Hosta-Plant.jpg', 'Hosta-Plant.jpg', '2025-04-28T20:45:00+02:00');

-- Insert Design2
INSERT INTO design2 (id, title) VALUES (1, 'Standaard ontwerp voor admin'),(2,'Mijn prachtige tuin');

-- Insert Users (gebruik BCrypt hashes!) designer: "geheim" admin: "super_geheim"
INSERT INTO users (id, username, password, enabled, role, creation_date, design_id) VALUES
                                                                                        (1, 'designer_test', '$2a$12$SRLVtg4dIFlCKFpvM5mSveFVVM5wid0p8nL3I.omNHEPBJu2aRI8a', TRUE, 'ROLE_DESIGNER', CURRENT_TIMESTAMP, 1),
                                                                                        (2, 'admin_test',   '$2a$12$jKxKvO8s/EKuTiEghAQCPeXnMEizilOSKjb4.AXBjWZms8kRC20ry', TRUE, 'ROLE_ADMIN', CURRENT_TIMESTAMP, 2);

-- Authorities (rollen)
INSERT INTO authorities (username, authority) VALUES
                                                  ('designer_test', 'ROLE_USER'),
                                                  ('admin_test',   'ROLE_ADMIN');

-- Insert Plants
INSERT INTO plants (id, dutch_name, latin_name, description, height, footprint, bloom_color_hex, bloom_color_group, published, locale_id, blooming_calendar_id, plantavatar_id)
VALUES
    (1, 'Klimroos New Dawn', 'Rosa New Dawn', 'Zachte roze klimroos die lang wordt.', 1.5, 1.0, '#FFE4E1', 'ROZE', true, 1, 1, 1),
    (2, 'Zonnebloem', 'Helianthus annuus', 'Zonnige hoge bloem met groot hart.', 2.0, 0.5, '#FFD700', 'GEEL', true, 2, 2, 2),
    (3, 'Hosta', 'Hosta sieboldiana', 'Schaduwminnende vaste plant met brede bladeren.', 0.6, 0.8, '#98FB98', 'GROEN', true, 3, 3, 3);

-- 3. Sequences aanpassen (optioneel, afhankelijk van je ORM/sequences)
SELECT setval('images_id_seq', (SELECT MAX(id) FROM images));
SELECT setval('plants_id_seq', (SELECT MAX(id) FROM plants));
SELECT setval('blooming_calendars_id_seq', (SELECT MAX(id) FROM blooming_calendars));
SELECT setval('locales_id_seq', (SELECT MAX(id) FROM locales));
SELECT setval('design2_id_seq', (SELECT MAX(id) FROM design2));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));