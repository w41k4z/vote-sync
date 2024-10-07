class PollingStationElections {
  final int id;
  final String electionType;
  final String name;
  final String electionDate;
  final int candidates;
  final int pollingStationId;

  const PollingStationElections({
    required this.id,
    required this.electionType,
    required this.name,
    required this.electionDate,
    required this.candidates,
    required this.pollingStationId,
  });

  // API mapping
  factory PollingStationElections.fromJson(
      Map<String, dynamic> json, int pollingStationId) {
    return PollingStationElections(
      id: json['id'],
      electionType: json['type']["type"],
      name: json['name'],
      electionDate: json['startDate'],
      candidates: json['candidates'],
      pollingStationId: pollingStationId,
    );
  }

  // Database mapping
  factory PollingStationElections.fromMap(Map<String, dynamic> json) {
    return PollingStationElections(
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
