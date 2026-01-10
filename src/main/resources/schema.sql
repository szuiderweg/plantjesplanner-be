
--clear tables (for development only, remove DROP statements for PROD)

DROP TABLE IF EXISTS plants CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS designs CASCADE;
DROP TABLE IF EXISTS image_metadata CASCADE;
DROP TABLE IF EXISTS blooming_calendars CASCADE;
DROP TABLE IF EXISTS locales CASCADE;
DROP TABLE IF EXISTS selected_plants CASCADE;

-- Table definitions for domain entities


CREATE TABLE locales (
                         id SERIAL PRIMARY KEY,
                         sunlight VARCHAR(32) NOT NULL,
                         moisture VARCHAR(32) NOT NULL,
                         wind_tolerance VARCHAR(32) NOT NULL,
                         soil_type VARCHAR(255) NOT NULL,
                         open_ground_only BOOLEAN NOT NULL
);

CREATE TABLE blooming_calendars (
                                    id SERIAL PRIMARY KEY,
                                    january BOOLEAN NOT NULL,
                                    february BOOLEAN NOT NULL,
                                    march BOOLEAN NOT NULL,
                                    april BOOLEAN NOT NULL,
                                    may BOOLEAN NOT NULL,
                                    june BOOLEAN NOT NULL,
                                    july BOOLEAN NOT NULL,
                                    august BOOLEAN NOT NULL,
                                    september BOOLEAN NOT NULL,
                                    october BOOLEAN NOT NULL,
                                    november BOOLEAN NOT NULL,
                                    december BOOLEAN NOT NULL
);

CREATE TABLE image_metadata (
                        id SERIAL PRIMARY KEY,
                        original_filename VARCHAR(255) NOT NULL,
                        stored_filename VARCHAR(255) NOT NULL,
                        upload_date_time TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE designs (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                        garden_size DOUBLE PRECISION,
                        locale_id INTEGER UNIQUE,
                             CONSTRAINT fk_users_designs FOREIGN KEY (locale_id) REFERENCES locales(id)

);

CREATE TABLE plants (
                        id SERIAL PRIMARY KEY,
                        dutch_name VARCHAR(255) NOT NULL,
                        latin_name VARCHAR(255) NOT NULL,
                        description TEXT,
                        height DOUBLE PRECISION,
                        footprint DOUBLE PRECISION,
                        bloom_color_hex VARCHAR(16),
                        bloom_color_group VARCHAR(64),
                        published BOOLEAN DEFAULT false,
                        locale_id INTEGER UNIQUE REFERENCES locales(id),
                        blooming_calendar_id INTEGER REFERENCES blooming_calendars(id),
                        plantavatar_id INTEGER REFERENCES image_metadata(id)
);

CREATE TABLE users (
                       username VARCHAR(255) PRIMARY KEY,
                       password VARCHAR(255) NOT NULL,
                       enabled BOOLEAN NOT NULL,
                       design_id BIGINT UNIQUE,
                        CONSTRAINT fk_users_designs FOREIGN KEY (design_id) REFERENCES designs(id)
);

CREATE TABLE authorities (
                             id SERIAL PRIMARY KEY,
                             username VARCHAR(255) NOT NULL,
                             authority VARCHAR(255) NOT NULL,
                             CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE
);

CREATE TABLE selected_plants(
                            id SERIAL PRIMARY KEY,
                            amount INTEGER NOT NULL,
                            design_id BIGINT NOT NULL,
                            plant_id BIGINT NOT NULL,
                            CONSTRAINT fk_selectedplants_designs FOREIGN KEY (design_id) REFERENCES designs(id) ON DELETE CASCADE,
                            CONSTRAINT fk_selectedplants_plants FOREIGN KEY (plant_id) REFERENCES plants(id),
                            CONSTRAINT uq_design_plant UNIQUE (design_id, plant_id)
);