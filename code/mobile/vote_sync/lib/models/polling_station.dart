class PollingStation {
  final int id;
  final String code;
  final String name;
  final String voteCenter;
  final String fokontany;
  final String commune;
  final String district;
  final String region;

  const PollingStation({
    required this.id,
    required this.code,
    required this.name,
    required this.voteCenter,
    required this.fokontany,
    required this.commune,
    required this.district,
    required this.region,
  });

  // Database mapping
  factory PollingStation.fromJson(Map<String, dynamic> json) {
    return PollingStation(
      id: json['id'],
      code: json['code'],
      name: json['name'],
      voteCenter: json['vote_center'],
      fokontany: json['fokontany'],
      commune: json['commune'],
      district: json['district'],
      region: json['region'],
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
    };
  }
}
