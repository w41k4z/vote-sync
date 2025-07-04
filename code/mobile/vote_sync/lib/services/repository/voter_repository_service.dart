import 'dart:math';

import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/env.dart';
import 'package:vote_sync/models/voter.dart';

class VoterRepositoryService {
  Future<void> create(Transaction tsx, Voter voter) async {
    await tsx.insert(
      "voters",
      voter.toMap(),
    );
  }

  Future<Map<String, dynamic>> findRegisteredVoters({
    required Database database,
    required String pollingStationId,
    required String electionId,
    int hasVoted = 0,
    String condition = "=",
    int page = 1,
    int size = Env.DEFAULT_PAGE_SIZE,
    String nic = "",
  }) async {
    final offset = (page - 1) * size;
    String rawQuery =
        'SELECT * FROM voters WHERE polling_station_id = ? AND election_id = ? AND has_voted $condition ?';
    String rawCountQuery =
        'SELECT COUNT(*) AS voters FROM voters WHERE polling_station_id = ? AND election_id = ? AND has_voted $condition ?';
    List<dynamic> arguments = [pollingStationId, electionId, hasVoted];
    List<dynamic> countArguments = [pollingStationId, electionId, hasVoted];
    if (nic.isNotEmpty) {
      rawQuery += ' AND nic LIKE ?';
      rawCountQuery += ' AND nic LIKE ?';
      arguments.add('$nic%');
      countArguments.add('$nic%');
    }
    rawQuery += ' ORDER BY nic ASC LIMIT ? OFFSET ?';
    arguments.add(size);
    arguments.add(offset);
    final List<Map<String, dynamic>> maps = await database.rawQuery(
      rawQuery,
      arguments,
    );
    final List<Map<String, dynamic>> countMaps =
        await database.rawQuery(rawCountQuery, countArguments);
    final int totalPage = max((countMaps.first['voters'] / size).ceil(), 1);
    Map<String, dynamic> result = {
      'voters': List.generate(maps.length, (i) {
        return Voter.fromMap(maps[i]);
      }),
      'totalPages': totalPage,
    };
    return result;
  }

  Future<void> register(
      {required Database database, required Voter voter}) async {
    voter.registrationDate = DateTime.now().toString();
    voter.hasVoted = 10;
    String query =
        "UPDATE voters set registration_date = ?, has_voted = ? WHERE id = ?";
    await database
        .rawUpdate(query, [voter.registrationDate, voter.hasVoted, voter.id]);
  }

  Future<bool> hasUnsyncedVoters({required Database database}) async {
    final List<Map<String, dynamic>> maps =
        await database.query('voters', where: "has_voted = 10");
    return maps.isNotEmpty;
  }

  Future<void> unregister(
      {required Database database, required Voter voter}) async {
    if (voter.hasVoted > 10) {
      throw Exception("Cannot unregister a voter who has been synced");
    }
    voter.registrationDate = null;
    voter.hasVoted = 0;
    String query =
        "UPDATE voters set registration_date = NULL, has_voted = ? WHERE id = ?";
    await database.rawUpdate(query, [voter.hasVoted, voter.id]);
  }

  Future<List<int>> totalPagesAndRows(
      {required Database database,
      int size = 10,
      String condition = "=",
      int hasVoted = 0}) async {
    final result = await database.rawQuery(
      'SELECT COUNT(*) FROM voters WHERE has_voted $condition ?',
      [hasVoted],
    );
    int totalRows = Sqflite.firstIntValue(result) ?? 0;
    return [max((totalRows / size).ceil(), 1), totalRows];
  }

  void syncVoters(
      {required Database database, required List<Voter> voters}) async {
    String query = "UPDATE voters set has_voted = ? WHERE id = ?";
    for (Voter voter in voters) {
      voter.hasVoted = 20;
      await database.rawUpdate(query, [20, voter.id]);
    }
  }

  void reinitialize(Database database) {
    database
        .rawUpdate("UPDATE VOTERS SET registration_date=NULL, has_voted=0", []);
  }

  Future<List<int>> getVotersCountAndRegisteredCount({
    required Database database,
    required String electionId,
    required String pollingStationId,
  }) async {
    String query1 =
        "SELECT COUNT(*) FROM VOTERS WHERE election_id = ? AND polling_station_id = ?";
    final result1 =
        await database.rawQuery(query1, [electionId, pollingStationId]);
    int voters = Sqflite.firstIntValue(result1) ?? 0;
    String query2 =
        "SELECT COUNT(*) FROM VOTERS WHERE has_voted = 20 AND election_id = ? AND polling_station_id = ?";
    final result2 =
        await database.rawQuery(query2, [electionId, pollingStationId]);
    int registered = Sqflite.firstIntValue(result2) ?? 0;
    return [voters, registered];
  }

  Future<void> deleteAll({
    required Transaction transaction,
    required String electionId,
    required String pollingStationId,
  }) async {
    await transaction.delete(
      'voters',
      where: 'election_id = ? AND polling_station_id = ?',
      whereArgs: [electionId, pollingStationId],
    );
  }
}
