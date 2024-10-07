class Voter {
  final int id;
  final String nic;
  final String name;
  final String firstName;
  final int gender;
  final int hasVoted;

  const Voter({
    required this.id,
    required this.nic,
    required this.name,
    required this.firstName,
    required this.gender,
    required this.hasVoted,
  });

  // API mapping
  factory Voter.fromJson(Map<String, dynamic> json) {
    return Voter(
      id: json['id'],
      nic: json['nic'],
      name: json['name'],
      firstName: json['firstName'],
      gender: json['gender'],
      hasVoted: 0,
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
    };
  }
}
