CREATE TABLE polling_stations (
    id INTEGER PRIMARY KEY,
    code TEXT UNIQUE NOT NULL,
    name TEXT NOT NULL,
    vote_center TEXT NOT NULL,
    fokontany TEXT NOT NULL,
    commune TEXT NOT NULL,
    district TEXT NOT NULL,
    region TEXT NOT NULL,
    province VARCHAR(12) NOT NULL
);

CREATE TABLE polling_station_elections (
    id INTEGER PRIMARY KEY,
    election_type TEXT NOT NULL,
    name TEXT NOT NULL,
    election_date TEXT NOT NULL,
    candidates INTEGER NOT NULL,
    registered_voters INTEGER NOT NULL,
    polling_station_id INTEGER NOT NULL,
    FOREIGN KEY (polling_station_id) REFERENCES polling_stations(id)
);

CREATE TABLE electors (
    id INTEGER PRIMARY KEY,
    nic TEXT UNIQUE NOT NULL,
    name TEXT NOT NULL,
    first_name TEXT,
    gender INTEGER NOT NULL,
    has_voted INTEGER NOT NULL -- 0: No, 1: Yes,
    polling_station_id INTEGER NOT NULL,
    FOREIGN KEY (polling_station_id) REFERENCES polling_stations(id)
);
CREATE INDEX idx_electors_name ON electors(name);
CREATE INDEX idx_electors_first_name ON electors(first_name);
CREATE INDEX idx_electors_has_voted ON electors(has_voted);