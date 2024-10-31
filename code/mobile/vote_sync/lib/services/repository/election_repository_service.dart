import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/election.dart';

class ElectionRepositoryService {
  Future<Election> create(Transaction tsx, Election election) async {
    await tsx.insert(
      "elections",
      election.toMap(),
      conflictAlgorithm: ConflictAlgorithm.ignore,
    );
    return election;
  }

  Future<Election?> findById(Database database, String electionId) async {
    final List<Map<String, dynamic>> maps = await database.query(
      'elections',
      where: 'id = ?',
      whereArgs: [electionId],
    );
    if (maps.isNotEmpty) {
      return Election.fromMap(maps.first);
    }
    return null;
  }
}
