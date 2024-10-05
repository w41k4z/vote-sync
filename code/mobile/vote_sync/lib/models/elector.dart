class Elector {
  final int id;
  final String nic;
  final String name;
  final String firstName;
  final int gender;
  final int hasVoted;

  const Elector({
    required this.id,
    required this.nic,
    required this.name,
    required this.firstName,
    required this.gender,
    required this.hasVoted,
  });

  // Database mapping
  factory Elector.fromJson(Map<String, dynamic> json) {
    return Elector(
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
