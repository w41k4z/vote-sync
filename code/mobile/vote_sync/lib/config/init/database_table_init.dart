class DatabaseTableInit {
  static const String CREATE_POLLING_STATION_TABLE = '''
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
    )
  ''';
  static const String CREATE_POLLING_STATION_ELECTION_TABLE = '''
    CREATE TABLE polling_station_elections (
        id INTEGER PRIMARY KEY,
        election_type TEXT NOT NULL,
        name TEXT NOT NULL,
        election_date TEXT NOT NULL,
        candidates INTEGER NOT NULL,
        polling_station_id INTEGER NOT NULL,
        FOREIGN KEY (polling_station_id) REFERENCES polling_stations (id)
    )
''';

  static const String CREATE_VOTER_TABLE = '''
    CREATE TABLE voters (
        id INTEGER PRIMARY KEY,
        nic TEXT UNIQUE NOT NULL,
        name TEXT NOT NULL,
        first_name TEXT,
        gender INTEGER NOT NULL,
        has_voted INTEGER NOT NULL,
        polling_station_id INTEGER NOT NULL,
        FOREIGN KEY (polling_station_id) REFERENCES polling_stations(id)
    )
  ''';

  static const String CREATE_CANDIDATE_TABLE = '''
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
    )
  ''';
}
