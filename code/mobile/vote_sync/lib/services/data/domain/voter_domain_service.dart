import 'dart:math';

import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/models/voter.dart';

class VoterDomainService {
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

  Future<Map<String, dynamic>> findRegisteredVoters(
      Database database, int hasVoted, int page, int size, String nic) async {
    final offset = (page - 1) * size;
    String rawQuery = 'SELECT * FROM voters WHERE has_voted = ?';
    String rawCountQuery =
        'SELECT COUNT(*) AS voters FROM voters WHERE has_voted = ?';
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
}
