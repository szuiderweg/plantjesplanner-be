
-- 2. Data inserts

-- Insert Locales
INSERT INTO locales (id, sunlight, moisture, wind_tolerance, soil_type, open_ground_only)
VALUES
    (1, 'HALFSCHADUW', 'DROOG', 'BESCHUT', 'tuinaarde, bosgrond', true),
    (2, 'ZONNIG', 'MATIG_VOCHTIG', 'BESCHUT', 'klei, zandgrond', false),
    (3, 'SCHADUW', 'VOCHTIG', 'STERKE_WIND', 'veen, humusgrond', true),
    (4, 'ZONNIG', 'MATIG_VOCHTIG', 'BESCHUT', 'zandgrond', false),
    (5, 'SCHADUW', 'NAT', 'GEMIDDELD', '', false);


-- Insert BloomingCalendars
INSERT INTO blooming_calendars (id, january, february, march, april, may, june, july, august, september, october, november, december)
VALUES
    (1, false, false, false, true, true, true, true, true, true, false, false, false),
    (2, false, false, true, true, true, false, false, false, false, false, false, false),
    (3, false, false, false, false, true, true, true, false, false, false, false, false);

-- Insert Images
INSERT INTO image_metadata (id, original_filename, stored_filename, upload_date_time)
VALUES (1, 'klimroos.jpg', 'klimroos.jpg', '2025-04-28T20:45:00+02:00'),
       (2, 'zonnebloem.jpg', 'zonnebloem.jpg', '2025-04-28T20:45:00+02:00'),
       (3, 'Hosta-Plant.jpg', 'Hosta-Plant.jpg', '2025-04-28T20:45:00+02:00');


-- Insert Plants
INSERT INTO plants (id, dutch_name, latin_name, description, height, footprint, bloom_color_hex, bloom_color_group, published, locale_id, blooming_calendar_id, plantavatar_id)
VALUES
    (1, 'Klimroos New Dawn', 'Rosa New Dawn', 'Zachte roze klimroos die lang wordt.', 1.5, 1.0, '#FFE4E1', 'ROZE', true, 1, 1, 1),
    (2, 'Zonnebloem', 'Helianthus annuus', 'Zonnige hoge bloem met groot hart.', 2.0, 0.5, '#FFD700', 'GEEL', true, 2, 2, 2),
    (3, 'Hosta', 'Hosta sieboldiana', 'Schaduwminnende vaste plant met brede bladeren.', 0.6, 0.8, '#98FB98', 'GROEN', true, 3, 3, 3);


-- Insert Design
INSERT INTO designs (id, title, garden_size, locale_id ) VALUES (1, 'Standaard ontwerp voor admin', 0.0, 5),(2,'Mijn prachtige tuin','15.0', 4);

-- Insert Users (use BCrypt hashes!) designer: "geheim" admin: "super_geheim" todo credentials verwijderen voor inleveren
INSERT INTO users (username, password, enabled,  design_id) VALUES
                                                                ( 'designer_test', '$2a$12$SRLVtg4dIFlCKFpvM5mSveFVVM5wid0p8nL3I.omNHEPBJu2aRI8a', TRUE,2),
                                                                ('admin_test',   '$2a$12$jKxKvO8s/EKuTiEghAQCPeXnMEizilOSKjb4.AXBjWZms8kRC20ry', TRUE,1);

-- Authorities (roles)
INSERT INTO authorities (id, username, authority) VALUES
                                                      (1, 'designer_test', 'ROLE_DESIGNER'),
                                                      (2, 'admin_test',   'ROLE_ADMIN');

-- selected plants for example design of designer_test
INSERT INTO selected_plants(id, amount, design_id, plant_id) VALUES (1, 3, 2, 2);


-- 3. set id number sequences
SELECT setval('image_metadata_id_seq', (SELECT MAX(id) FROM image_metadata));
SELECT setval('plants_id_seq', (SELECT MAX(id) FROM plants));
SELECT setval('blooming_calendars_id_seq', (SELECT MAX(id) FROM blooming_calendars));
SELECT setval('locales_id_seq', (SELECT MAX(id) FROM locales));
SELECT setval('designs_id_seq', (SELECT MAX(id) FROM designs));
SELECT setval('authorities_id_seq',(SELECT MAX(id) FROM authorities));
-- SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));