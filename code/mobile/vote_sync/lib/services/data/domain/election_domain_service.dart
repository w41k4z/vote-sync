import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/polling_station_election.dart';

class ElectionDomainService {
  Future<PollingStationElections> create(
      Transaction tsx, PollingStationElections pollingStationElections) async {
    await tsx.insert(
      "polling_station_elections",
      pollingStationElections.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return pollingStationElections;
  }

  Future<PollingStationElections?> findById(
      Database database, int pollingStationElectionId) async {
    final List<Map<String, dynamic>> maps = await database.query(
      'polling_station_elections',
      where: 'id = ?',
      whereArgs: [pollingStationElectionId],
    );
    if (maps.isNotEmpty) {
      return PollingStationElections.fromMap(maps.first);
    }
    return null;
  }
}
