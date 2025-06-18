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

-- Insert Plants
INSERT INTO plantcatalogue (id, dutch_name, latin_name, description, height, footprint, bloom_color_hex, bloom_color_group, published, locale_id, blooming_calendar_id, plantavatar_id)
VALUES
    (1, 'Klimroos New Dawn', 'Rosa New Dawn', 'Zachte roze klimroos die lang wordt.', 1.5, 1.0, '#FFE4E1', 'ROZE', true, 1, 1, 1),
    (2, 'Zonnebloem', 'Helianthus annuus', 'Zonnige hoge bloem met groot hart.', 2.0, 0.5, '#FFD700', 'GEEL', true, 2, 2, 2),
    (3, 'Hosta', 'Hosta sieboldiana', 'Schaduwminnende vaste plant met brede bladeren.', 0.6, 0.8, '#98FB98', 'GROEN', true, 3, 3, 3);

-- - Set sequence for primary key value (usually "id") generators to current max value to avoid duplicate key errors when using the API
SELECT setval('images_seq', (SELECT MAX(id) FROM images));
SELECT setval('plants_seq', (SELECT MAX(id) FROM plants));
SELECT setval('blooming_calendars_seq', (SELECT MAX(id) FROM blooming_calendars));
SELECT setval('locales_seq', (SELECT MAX(id) FROM locales));
SELECT setval('moodboarditems_seq', (SELECT MAX(id) FROM moodboarditems));
SELECT setval('users_seq', (SELECT MAX(id) FROM users));
