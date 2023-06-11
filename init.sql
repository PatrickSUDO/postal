CREATE SCHEMA IF NOT EXISTS postal;

CREATE TABLE IF NOT EXISTS postal.postcodes (
    id INTEGER PRIMARY KEY,
    postcode VARCHAR(10) NOT NULL,
    latitude FLOAT8,
    longitude FLOAT8
);

COPY postal.postcodes(id, postcode, latitude, longitude)
FROM '/var/lib/postgresql/csv/ukpostcodes.csv'
DELIMITER ','
CSV HEADER;