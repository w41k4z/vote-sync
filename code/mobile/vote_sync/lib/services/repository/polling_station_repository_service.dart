import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/polling_station.dart';

class PollingStationRepositoryService {
  Future<void> create(Transaction tsx, PollingStation pollingStation) async {
    await tsx.insert(
      "polling_stations",
      pollingStation.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  Future<PollingStation?> findByIdAndElectionId(
    Database database,
    int pollingStationId,
    int electionId,
  ) async {
    final List<Map<String, dynamic>> maps = await database.query(
      'polling_stations',
      where: 'id = ? AND election_id = ?',
      whereArgs: [pollingStationId, electionId],
    );
    if (maps.isNotEmpty) {
      return PollingStation.fromMap(maps.first);
    }
    return null;
  }
}
