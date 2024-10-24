import 'package:get_it/get_it.dart';
import 'package:vote_sync/env.dart';
import 'package:vote_sync/services/api/api_call_service.dart';
import 'package:vote_sync/services/local_storage_service.dart';

class Candidate {
  int id;
  int registrationId;
  String registrationDate;
  int candidateNumber;
  String information;
  String politicalEntity;
  String politicalEntityDescription;
  String imagePath;
  int pollingStationId;

  Candidate({
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

  // API mapping
  factory Candidate.fromJson(Map<String, dynamic> json, int pollingStationId) {
    return Candidate(
      id: json['id'],
      registrationId: json['registrationId'],
      registrationDate: json['registrationDate'],
      candidateNumber: json['candidateNumber'],
      information: json['information'],
      politicalEntity: json['politicalEntity'],
      politicalEntityDescription: json['politicalEntityDescription'],
      imagePath: json['imagePath'],
      pollingStationId: pollingStationId,
    );
  }

  // Database mapping
  factory Candidate.fromMap(Map<String, dynamic> json) {
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

  Future<void> downloadCandidateImage(ApiCallService callService) async {
    LocalStorageService localStorageService =
        GetIt.I.get<LocalStorageService>();
    String localFilePath = "${localStorageService.appDocDir.path}/$imagePath";
    String filePath = "${Env.BASE_URL}/public/$imagePath";
    await callService.downloadFile(filePath, localFilePath);
  }
}
