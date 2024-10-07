import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/candidate.dart';

class CandidateDomainService {
  Future<void> create(Transaction tsx, Candidate candidate) async {
    await tsx.insert(
      "candidates",
      candidate.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  Future<List<Candidate>> findAll(Database database) async {
    final List<Map<String, dynamic>> maps = await database.query('candidates');
    return List.generate(maps.length, (i) {
      return Candidate.fromMap(maps[i]);
    });
  }
}
