
--clear tables

DROP TABLE IF EXISTS plants CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS design CASCADE;
DROP TABLE IF EXISTS images CASCADE;
DROP TABLE IF EXISTS blooming_calendars CASCADE;
DROP TABLE IF EXISTS locales CASCADE;




-- 1. Tabellendefinities


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

CREATE TABLE images (
                        id SERIAL PRIMARY KEY,
                        original_filename VARCHAR(255) NOT NULL,
                        stored_filename VARCHAR(255) NOT NULL,
                        upload_date_time TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE design (
                         id SERIAL PRIMARY KEY,
                         title VARCHAR(255) NOT NULL
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       enabled BOOLEAN NOT NULL,
                       role VARCHAR(64) NOT NULL,
                       creation_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       design_id INTEGER REFERENCES design(id)
);

CREATE TABLE authorities (
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

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
                        locale_id INTEGER REFERENCES locales(id),
                        blooming_calendar_id INTEGER REFERENCES blooming_calendars(id),
                        plantavatar_id INTEGER REFERENCES images(id)
);

-- 2. Data inserts
