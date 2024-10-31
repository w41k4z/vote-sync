import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/polling_station.dart';

class PollingStationRepositoryService {
  Future<void> create(Transaction tsx, PollingStation pollingStation) async {
    await tsx.insert(
      "polling_stations",
      pollingStation.toMap(),
    );
  }

  Future<PollingStation?> findByIdAndElectionId(
    Database database,
    String pollingStationId,
    String electionId,
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

  Future<void> updateNullAndBlankVotes({
    required Database database,
    required PollingStation pollingStation,
  }) async {
    String query =
        "UPDATE polling_stations SET nulls = ?, blanks = ? WHERE id = ? AND election_id = ?";
    await database.rawUpdate(query, [
      pollingStation.nulls,
      pollingStation.blanks,
      pollingStation.id,
      pollingStation.electionId,
    ]);
  }
}
