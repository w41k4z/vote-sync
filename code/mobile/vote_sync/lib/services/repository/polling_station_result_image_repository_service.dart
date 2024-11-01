import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/polling_station_result_image.dart';

class PollingStationResultImageRepositoryService {
  Future<List<PollingStationResultImage>> createAll({
    required Database database,
    required String pollingStationId,
    required String electionId,
    required List<String> imagesPath,
  }) async {
    List<PollingStationResultImage> pollingStationResultImages = [];
    await database.transaction((tsx) async {
      await tsx.delete(
        "polling_station_result_images",
        where: "polling_station_id = ? AND election_id = ?",
        whereArgs: [
          pollingStationId,
          electionId,
        ],
      );
      for (String imagePath in imagesPath) {
        PollingStationResultImage pollingStationResultImage =
            PollingStationResultImage(
          pollingStationId: int.parse(pollingStationId),
          electionId: int.parse(electionId),
          imagePath: imagePath,
        );
        await pollingStationResultImage.copyImageToAppDirectory();
        await tsx.insert(
          "polling_station_result_images",
          pollingStationResultImage.toMap(),
        );
        pollingStationResultImages.add(pollingStationResultImage);
      }
    });
    return pollingStationResultImages;
  }

  Future<List<PollingStationResultImage>>
      findAllByPollingStationIdAndElectionId({
    required Database database,
    required String pollingStationId,
    required String electionId,
  }) async {
    final List<Map<String, dynamic>> maps = await database.query(
      'polling_station_result_images',
      where: 'polling_station_id = ? AND election_id = ?',
      whereArgs: [pollingStationId, electionId],
    );
    return List.generate(maps.length, (i) {
      return PollingStationResultImage.fromMap(maps[i]);
    });
  }
}
