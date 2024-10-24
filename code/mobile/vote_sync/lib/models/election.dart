class Election {
  int id;
  String electionType;
  String name;
  String electionDate;
  int candidates;
  int pollingStationId;

  Election({
    required this.id,
    required this.electionType,
    required this.name,
    required this.electionDate,
    required this.candidates,
    required this.pollingStationId,
  });

  // API mapping
  factory Election.fromJson(Map<String, dynamic> json, int pollingStationId) {
    return Election(
      id: json['id'],
      electionType: json['type']["type"],
      name: json['name'],
      electionDate: json['startDate'],
      candidates: json['candidates'],
      pollingStationId: pollingStationId,
    );
  }

  // Database mapping
  factory Election.fromMap(Map<String, dynamic> json) {
    return Election(
      id: json['id'],
      electionType: json['election_type'],
      name: json['name'],
      candidates: json['candidates'],
      electionDate: json['election_date'],
      pollingStationId: json['polling_station_id'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'election_type': electionType,
      'name': name,
      'election_date': electionDate,
      'candidates': candidates,
      'polling_station_id': pollingStationId,
    };
  }
}
