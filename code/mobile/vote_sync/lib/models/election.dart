class Election {
  int id;
  String electionType;
  String name;
  String electionDate;

  Election({
    required this.id,
    required this.electionType,
    required this.name,
    required this.electionDate,
  });

  // API mapping
  factory Election.fromJson(Map<String, dynamic> json) {
    return Election(
      id: json['id'],
      electionType: json['type']["type"],
      name: json['name'],
      electionDate: json['startDate'],
    );
  }

  // Database mapping
  factory Election.fromMap(Map<String, dynamic> json) {
    return Election(
      id: json['id'],
      electionType: json['election_type'],
      name: json['name'],
      electionDate: json['election_date'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'election_type': electionType,
      'name': name,
      'election_date': electionDate,
    };
  }
}
