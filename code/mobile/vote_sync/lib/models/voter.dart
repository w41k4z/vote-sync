class Voter {
  final int id;
  final String nic;
  final String name;
  final String firstName;
  final int gender;
  final int hasVoted;
  final int pollingStationId;

  const Voter({
    required this.id,
    required this.nic,
    required this.name,
    required this.firstName,
    required this.gender,
    required this.hasVoted,
    required this.pollingStationId,
  });

  // API mapping
  factory Voter.fromJson(Map<String, dynamic> json, int pollingStationId) {
    return Voter(
      id: json['id'],
      nic: json['nic'],
      name: json['name'],
      firstName: json['firstName'],
      gender: json['gender'],
      hasVoted: 0,
      pollingStationId: pollingStationId,
    );
  }

  // Database mapping
  factory Voter.fromMap(Map<String, dynamic> json) {
    return Voter(
      id: json['id'],
      nic: json['nic'],
      name: json['name'],
      firstName: json['first_name'],
      gender: json['gender'],
      hasVoted: json['has_voted'],
      pollingStationId: json['polling_station_id'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'nic': nic,
      'name': name,
      'first_name': firstName,
      'gender': gender,
      'has_voted': hasVoted,
      'polling_station_id': pollingStationId,
    };
  }
}
