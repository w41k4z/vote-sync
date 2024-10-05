import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:vote_sync/services/data/init/database_index_init.dart';
import 'package:vote_sync/services/data/init/database_table_init.dart';

class DatabaseManager {
  static const _databaseVersion = 1;
  static const _databaseName = 'vote_sync.db';
  Database? _database;

  Future<Database> get database async {
    _database ??= await _initDatabase();
    return _database!;
  }

  Future<Database> _initDatabase() async {
    String path = join(await getDatabasesPath(), _databaseName);
    return openDatabase(
      path,
      version: _databaseVersion,
      onCreate: (db, version) async {
        // Tables
        await db.execute(DatabaseTableInit.CREATE_POLLING_STATION_TABLE);
        await db
            .execute(DatabaseTableInit.CREATE_POLLING_STATION_ELECTION_TABLE);
        await db.execute(DatabaseTableInit.CREATE_ELECTOR_TABLE);
        // Indexes
        await db.execute(DatabaseIndexInit.CREATE_ELECTORS_NAME_IDX);
        await db.execute(DatabaseIndexInit.CREATE_ELECTORS_FIRST_NAME_IDX);
        await db.execute(DatabaseIndexInit.CREATE_ELECTORS_HAS_VOTE_IDX);
      },
    );
  }
}
