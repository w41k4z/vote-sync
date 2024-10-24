import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/election.dart';

class ElectionRepositoryService {
  Future<Election> create(Transaction tsx, Election election) async {
    await tsx.insert(
      "polling_station_elections",
      election.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return election;
  }

  Future<Election?> findById(
      Database database, int pollingStationElectionId) async {
    final List<Map<String, dynamic>> maps = await database.query(
      'polling_station_elections',
      where: 'id = ?',
      whereArgs: [pollingStationElectionId],
    );
    if (maps.isNotEmpty) {
      return Election.fromMap(maps.first);
    }
    return null;
  }
}
