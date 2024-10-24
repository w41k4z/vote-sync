import 'dart:math';

import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/env.dart';
import 'package:vote_sync/models/voter.dart';

class VoterRepositoryService {
  Future<void> create(Transaction tsx, Voter voter) async {
    await tsx.insert(
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

  Future<Map<String, dynamic>> findRegisteredVoters({
    required Database database,
    int hasVoted = 0,
    String condition = "=",
    int page = 1,
    int size = Env.DEFAULT_PAGE_SIZE,
    String nic = "",
  }) async {
    final offset = (page - 1) * size;
    String rawQuery = 'SELECT * FROM voters WHERE has_voted $condition ?';
    String rawCountQuery =
        'SELECT COUNT(*) AS voters FROM voters WHERE has_voted $condition ?';
    List<dynamic> arguments = [hasVoted];
    List<dynamic> countArguments = [hasVoted];
    if (nic.isNotEmpty) {
      rawQuery += ' AND nic LIKE ?';
      rawCountQuery += ' AND nic LIKE ?';
      arguments.add('$nic%');
      countArguments.add('$nic%');
    }
    rawQuery += ' LIMIT ? OFFSET ?';
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
      {required Database database, int size = 10}) async {
    final result = await database.rawQuery('SELECT COUNT(*) FROM voters');
    int totalRows = Sqflite.firstIntValue(result) ?? 0;
    return [max((totalRows / size).ceil(), 1), totalRows];
  }
}
