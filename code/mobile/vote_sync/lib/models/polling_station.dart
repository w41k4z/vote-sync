class PollingStation {
  final int id;
  final String code;
  final String name;
  final String voteCenter;
  final String fokontany;
  final String commune;
  final String district;
  final String region;
  final int registeredVoters;

  const PollingStation({
    required this.id,
    required this.code,
    required this.name,
    required this.voteCenter,
    required this.fokontany,
    required this.commune,
    required this.district,
    required this.region,
    required this.registeredVoters,
  });

  // API mapping
  factory PollingStation.fromJson(Map<String, dynamic> json) {
    return PollingStation(
      id: json['id'],
      code: json['pollingStationCode'],
      name: json['pollingStation'],
      voteCenter: json['votingCenter'],
      fokontany: json['fokontany'],
      commune: json['commune'],
      district: json['district'],
      region: json['region'],
      registeredVoters: json['registeredVoters'],
    );
  }

  // Database mapping
  factory PollingStation.fromMap(Map<String, dynamic> json) {
    return PollingStation(
      id: json['id'],
      code: json['code'],
      name: json['name'],
      voteCenter: json['vote_center'],
      fokontany: json['fokontany'],
      commune: json['commune'],
      district: json['district'],
      region: json['region'],
      registeredVoters: json['registered_voters'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'code': code,
      'name': name,
      'vote_center': voteCenter,
      'fokontany': fokontany,
      'commune': commune,
      'district': district,
      'region': region,
      'registered_voters': registeredVoters,
    };
  }
}
