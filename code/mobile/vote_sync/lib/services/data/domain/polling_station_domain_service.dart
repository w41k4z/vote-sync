import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/polling_station.dart';

class PollingStationDomainService {
  Future<void> create(Database database, PollingStation pollingStation) async {
    await database.insert(
      "polling_stations",
      pollingStation.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  Future<PollingStation?> findById(
      Database database, int pollingStationId) async {
    final List<Map<String, dynamic>> maps = await database.query(
      'polling_stations',
      where: 'id = ?',
      whereArgs: [pollingStationId],
    );
    if (maps.isNotEmpty) {
      return PollingStation.fromMap(maps.first);
    }
    return null;
  }
}
