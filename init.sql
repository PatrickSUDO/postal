CREATE SCHEMA IF NOT EXISTS ukpostal;

CREATE TABLE IF NOT EXISTS ukpostal.postcodes (
    id INTEGER PRIMARY KEY,
    postcode VARCHAR(10) NOT NULL,
    latitude FLOAT8 NOT NULL,
    longitude FLOAT8 NOT NULL
);

COPY ukpostal.postcodes(id, postcode, latitude, longitude)
FROM '/csv/ukpostcodes.csv'
DELIMITER ','
CSV HEADER;