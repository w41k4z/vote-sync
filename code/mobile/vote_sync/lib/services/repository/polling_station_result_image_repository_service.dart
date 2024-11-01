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
    List<PollingStationResultImage> existingResultImages =
        await findAllByPollingStationIdAndElectionId(
      database: database,
      pollingStationId: pollingStationId,
      electionId: electionId,
    );
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
    for (PollingStationResultImage existingResultImage
        in existingResultImages) {
      await existingResultImage.deleteImage();
    }
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

  Future<void> deleteImages({
    required Transaction transaction,
    required String electionId,
    required String pollingStationId,
  }) async {
    final List<Map<String, dynamic>> maps = await transaction.query(
      'polling_station_result_images',
      where: 'polling_station_id = ? AND election_id = ?',
      whereArgs: [pollingStationId, electionId],
    );
    List<PollingStationResultImage> pollingStationResultImages =
        List.generate(maps.length, (i) {
      return PollingStationResultImage.fromMap(maps[i]);
    });
    for (PollingStationResultImage pollingStationResultImage
        in pollingStationResultImages) {
      await pollingStationResultImage.deleteImage();
    }
    await transaction.delete(
      "polling_station_result_images",
      where: "polling_station_id = ? AND election_id = ?",
      whereArgs: [
        pollingStationId,
        electionId,
      ],
    );
  }
}
