class PollingStationElections {
  final String id;
  final String electionType;
  final String name;
  final String electionDate;
  final int candidates;
  final int registeredVoters;
  final int pollingStationId;

  PollingStationElections({
    required this.id,
    required this.electionType,
    required this.name,
    required this.electionDate,
    required this.candidates,
    required this.registeredVoters,
    required this.pollingStationId,
  });

  // Database mapping
  factory PollingStationElections.fromJson(Map<String, dynamic> json) {
    return PollingStationElections(
      id: json['id'],
      electionType: json['election_type'],
      name: json['name'],
      candidates: json['candidates'],
      registeredVoters: json['registered_voters'],
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
      'registered_voters': registeredVoters,
      'polling_station_id': pollingStationId,
    };
  }
}
