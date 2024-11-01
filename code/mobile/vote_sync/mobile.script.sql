CREATE TABLE elections (
    id INTEGER PRIMARY KEY,
    election_type TEXT NOT NULL,
    name TEXT NOT NULL,
    election_date TEXT NOT NULL
);

CREATE TABLE polling_stations (
    id INTEGER,
    election_id INTEGER,
    code TEXT NOT NULL,
    name TEXT NOT NULL,
    vote_center TEXT NOT NULL,
    fokontany TEXT NOT NULL,
    commune TEXT NOT NULL,
    district TEXT NOT NULL,
    region TEXT NOT NULL,
    registered_voters INTEGER NOT NULL,
    candidates INTEGER NOT NULL,
    nulls INTEGER NOT NULL,
    blanks INTEGER NOT NULL,
    synced INTEGER NOT NULL,
    CHECK(nulls >= 0 AND blanks >= 0),
    PRIMARY KEY(id, election_id),
    FOREIGN KEY(election_id) REFERENCES elections(id)
);

CREATE TABLE polling_station_result_images (
    polling_station_id INTEGER,
    election_id INTEGER,
    image_path TEXT,
    PRIMARY KEY(polling_station_id, election_id, image_path),
    FOREIGN KEY(polling_station_id) REFERENCES polling_stations(id),
    FOREIGN KEY(election_id) REFERENCES elections(id)
);

CREATE TABLE voters (
    id INTEGER,
    nic TEXT NOT NULL,
    name TEXT NOT NULL,
    first_name TEXT,
    gender INTEGER NOT NULL,
    has_voted INTEGER NOT NULL -- 0: No, 10: Yes, 20: Synchronized
    polling_station_id INTEGER NOT NULL,
    election_id INTEGER,
    registration_date TEXT,
    PRIMARY KEY(id, election_id),
    FOREIGN KEY(election_id) REFERENCES elections(id),
    FOREIGN KEY(polling_station_id) REFERENCES polling_stations(id)
);
CREATE INDEX idx_voters_name ON voters(name);
CREATE INDEX idx_voters_first_name ON voters(first_name);
CREATE INDEX idx_voters_has_voted ON voters(has_voted);

CREATE TABLE candidates (
    id INTEGER,
    registration_id INTEGER NOT NULL,
    election_id INTEGER,
    registration_date TEXT NOT NULL,
    candidate_number INTEGER NOT NULL,
    information TEXT NOT NULL,
    political_entity TEXT NOT NULL,
    political_entity_description TEXT NOT NULL,
    image_path TEXT NOT NULL,
    polling_station_id INTEGER NOT NULL,
    votes INTEGER NOT NULL,
    CHECK(votes >= 0),
    PRIMARY KEY(id, election_id, polling_station_id),
    FOREIGN KEY(election_id) REFERENCES elections(id),
    FOREIGN KEY(polling_station_id) REFERENCES polling_stations(id)
);