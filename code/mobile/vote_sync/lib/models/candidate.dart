class Candidate {
  final int id;
  final int registrationId;
  final String registrationDate;
  final int candidateNumber;
  final String information;
  final String politicalEntity;
  final String politicalEntityDescription;
  final String imagePath;
  final int pollingStationId;

  const Candidate({
    required this.id,
    required this.registrationId,
    required this.registrationDate,
    required this.candidateNumber,
    required this.information,
    required this.politicalEntity,
    required this.politicalEntityDescription,
    required this.imagePath,
    required this.pollingStationId,
  });

  // Database mapping
  factory Candidate.fromJson(Map<String, dynamic> json) {
    return Candidate(
      id: json['id'],
      registrationId: json['registration_id'],
      registrationDate: json['registration_date'],
      candidateNumber: json['candidate_number'],
      information: json['information'],
      politicalEntity: json['political_entity'],
      politicalEntityDescription: json['political_entity_description'],
      imagePath: json['image_path'],
      pollingStationId: json['polling_station_id'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'registration_id': registrationId,
      'registration_date': registrationDate,
      'candidate_number': candidateNumber,
      'information': information,
      'political_entity': politicalEntity,
      'political_entity_description': politicalEntityDescription,
      'image_path': imagePath,
      'polling_station_id': pollingStationId,
    };
  }
}
