CREATE TABLE polling_stations (
    id INTEGER PRIMARY KEY,
    code TEXT UNIQUE NOT NULL,
    name TEXT NOT NULL,
    vote_center TEXT NOT NULL,
    fokontany TEXT NOT NULL,
    commune TEXT NOT NULL,
    district TEXT NOT NULL,
    region TEXT NOT NULL,
    registered_voters INTEGER NOT NULL,
);

CREATE TABLE polling_station_elections (
    id INTEGER PRIMARY KEY,
    election_type TEXT NOT NULL,
    name TEXT NOT NULL,
    election_date TEXT NOT NULL,
    candidates INTEGER NOT NULL,
    polling_station_id INTEGER NOT NULL,
    FOREIGN KEY (polling_station_id) REFERENCES polling_stations(id)
);

CREATE TABLE voters (
    id INTEGER PRIMARY KEY,
    nic TEXT UNIQUE NOT NULL,
    name TEXT NOT NULL,
    first_name TEXT,
    gender INTEGER NOT NULL,
    has_voted INTEGER NOT NULL -- 0: No, 1: Yes,
    polling_station_id INTEGER NOT NULL,
    registration_date TEXT,
    FOREIGN KEY (polling_station_id) REFERENCES polling_stations(id)
);
CREATE INDEX idx_voters_name ON voters(name);
CREATE INDEX idx_voters_first_name ON voters(first_name);
CREATE INDEX idx_voters_has_voted ON voters(has_voted);

CREATE TABLE candidates (
    id INTEGER PRIMARY KEY,
    registration_id INTEGER NOT NULL,
    registration_date TEXT NOT NULL,
    candidate_number INTEGER NOT NULL,
    information TEXT NOT NULL,
    political_entity TEXT NOT NULL,
    political_entity_description TEXT NOT NULL,
    image_path TEXT NOT NULL,
    polling_station_id INTEGER NOT NULL,
    FOREIGN KEY (polling_station_id) REFERENCES polling_stations(id)
);