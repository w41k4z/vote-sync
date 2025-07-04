class PollingStation {
  int id;
  int electionId;
  String code;
  String name;
  String voteCenter;
  String fokontany;
  String commune;
  String district;
  String region;
  int registeredVoters;
  int candidates;
  int nulls;
  int blanks;
  int synced;

  PollingStation({
    required this.id,
    required this.electionId,
    required this.code,
    required this.name,
    required this.voteCenter,
    required this.fokontany,
    required this.commune,
    required this.district,
    required this.region,
    required this.registeredVoters,
    required this.candidates,
    required this.nulls,
    required this.blanks,
    required this.synced,
  });

  // API mapping
  factory PollingStation.fromJson(Map<String, dynamic> json, int electionId) {
    return PollingStation(
      id: json['id'],
      electionId: electionId,
      code: json['pollingStationCode'],
      name: json['pollingStation'],
      voteCenter: json['votingCenter'],
      fokontany: json['fokontany'],
      commune: json['commune'],
      district: json['district'],
      region: json['region'],
      registeredVoters: json['voters'],
      candidates: json['candidates'],
      nulls: 0,
      blanks: 0,
      synced: 0,
    );
  }

  // Database mapping
  factory PollingStation.fromMap(Map<String, dynamic> json) {
    return PollingStation(
      id: json['id'],
      electionId: json['election_id'],
      code: json['code'],
      name: json['name'],
      voteCenter: json['vote_center'],
      fokontany: json['fokontany'],
      commune: json['commune'],
      district: json['district'],
      region: json['region'],
      registeredVoters: json['registered_voters'],
      candidates: json['candidates'],
      nulls: json['nulls'],
      blanks: json['blanks'],
      synced: json['synced'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'election_id': electionId,
      'code': code,
      'name': name,
      'vote_center': voteCenter,
      'fokontany': fokontany,
      'commune': commune,
      'district': district,
      'region': region,
      'registered_voters': registeredVoters,
      'candidates': candidates,
      'nulls': nulls,
      'blanks': nulls,
      'synced': synced,
    };
  }

  bool isSynced() {
    return synced == 20;
  }
}
