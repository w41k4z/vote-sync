import 'dart:io';

import 'package:get_it/get_it.dart';
import 'package:vote_sync/services/local_storage_service.dart';
import 'package:path/path.dart' as path;

class PollingStationResultImage {
  final int pollingStationId;
  final int electionId;
  String imagePath;

  PollingStationResultImage({
    required this.pollingStationId,
    required this.electionId,
    required this.imagePath,
  });

  // Database mapping
  factory PollingStationResultImage.fromMap(Map<String, dynamic> json) {
    return PollingStationResultImage(
      pollingStationId: json['polling_station_id'],
      electionId: json['election_id'],
      imagePath: json['image_path'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'polling_station_id': pollingStationId,
      'election_id': electionId,
      'image_path': imagePath,
    };
  }

  Future<void> copyImageToAppDirectory() async {
    LocalStorageService localStorageService =
        GetIt.I.get<LocalStorageService>();
    final String timestamp = DateTime.now().millisecondsSinceEpoch.toString();
    String newImagePath =
        '$electionId/result/$pollingStationId/$electionId-$pollingStationId-$timestamp.webp';
    String localFilePath = path.join(
      localStorageService.appDocDir.path,
      newImagePath,
    );
    await Directory(path.dirname(localFilePath)).create(recursive: true);
    File imageFile = File(imagePath);
    await imageFile.copy(localFilePath);
    imagePath = newImagePath;
  }

  Future<void> deleteImage() async {
    LocalStorageService localStorageService =
        GetIt.I.get<LocalStorageService>();
    File imageFile = File(path.join(
      localStorageService.appDocDir.path,
      imagePath,
    ));
    await imageFile.delete();
  }
}
