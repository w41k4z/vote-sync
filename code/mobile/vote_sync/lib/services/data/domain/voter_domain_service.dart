import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/voter.dart';

class VoterDomainService {
  Future<void> create(Database database, Voter voter) async {
    await database.insert(
      "voters",
      voter.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  Future<List<Voter>> findAll(Database database) async {
    final List<Map<String, dynamic>> maps = await database.query('voters');
    return List.generate(maps.length, (i) {
      return Voter.fromMap(maps[i]);
    });
  }
}
