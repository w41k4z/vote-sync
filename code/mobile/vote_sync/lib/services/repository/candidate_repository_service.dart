import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/candidate.dart';

class CandidateRepositoryService {
  Future<void> create(Transaction tsx, Candidate candidate) async {
    await tsx.insert(
      "candidates",
      candidate.toMap(),
    );
  }

  Future<List<Candidate>> findAll({
    required Database database,
    required String pollingStationId,
    required String electionId,
    String information = '',
  }) async {
    String query =
        "SELECT * FROM candidates WHERE polling_station_id = ? AND election_id = ?";
    List<dynamic> arguments = [pollingStationId, electionId];
    if (information.isNotEmpty) {
      query += " AND information LIKE ?";
      arguments.add('%$information%');
    }
    final List<Map<String, dynamic>> maps =
        await database.rawQuery(query, arguments);
    return List.generate(maps.length, (i) {
      return Candidate.fromMap(maps[i]);
    });
  }

  Future<void> updateVote({
    required Database database,
    required Candidate candidate,
  }) async {
    String query =
        "UPDATE candidates SET votes = ? where id = ? AND election_id = ? AND polling_station_id = ?";
    await database.rawUpdate(query, [
      candidate.votes,
      candidate.id,
      candidate.electionId,
      candidate.pollingStationId,
    ]);
  }

  Future<void> deleteAll({
    required Transaction transaction,
    required String electionId,
    required String pollingStationId,
  }) async {
    await transaction.delete(
      "candidates",
      where: "election_id = ? AND polling_station_id = ?",
      whereArgs: [electionId, pollingStationId],
    );
  }
}
