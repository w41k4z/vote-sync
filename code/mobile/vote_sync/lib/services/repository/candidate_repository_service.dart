import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/candidate.dart';

class CandidateRepositoryService {
  Future<void> create(Transaction tsx, Candidate candidate) async {
    await tsx.insert(
      "candidates",
      candidate.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  Future<List<Candidate>> findAll(Database database, String information) async {
    String query = "SELECT * FROM candidates";
    List<dynamic> arguments = [];
    if (information.isNotEmpty) {
      query += " WHERE information LIKE ?";
      arguments.add('%$information%');
    }
    final List<Map<String, dynamic>> maps =
        await database.rawQuery(query, arguments);
    return List.generate(maps.length, (i) {
      return Candidate.fromMap(maps[i]);
    });
  }
}
