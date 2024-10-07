import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:vote_sync/models/candidate.dart';
import 'package:vote_sync/models/polling_station.dart';
import 'package:vote_sync/models/polling_station_election.dart';
import 'package:vote_sync/models/voter.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/data/domain/candidate_domain_service.dart';
import 'package:vote_sync/services/data/domain/election_domain_service.dart';
import 'package:vote_sync/services/data/domain/polling_station_domain_service.dart';
import 'package:vote_sync/services/data/domain/voter_domain_service.dart';
import 'package:vote_sync/config/init/database_index_init.dart';
import 'package:vote_sync/config/init/database_table_init.dart';

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
        await db.execute(DatabaseTableInit.CREATE_VOTER_TABLE);
        await db.execute(DatabaseTableInit.CREATE_CANDIDATE_TABLE);
        // Indexes
        await db.execute(DatabaseIndexInit.CREATE_VOTERS_NAME_IDX);
        await db.execute(DatabaseIndexInit.CREATE_VOTERS_FIRST_NAME_IDX);
        await db.execute(DatabaseIndexInit.CREATE_VOTERS_HAS_VOTE_IDX);
      },
    );
  }

  // Care to call this method after granting access to the app
  Future<bool> isDatabasePopulated() async {
    String pollingStationId = GetIt.I.get<AppInstance>().getPollingStationId();
    Database databaseInstance = await database;
    PollingStation? pollingStation = await GetIt.I
        .get<PollingStationDomainService>()
        .findById(databaseInstance, int.parse(pollingStationId));
    return pollingStation != null;
  }

  Future<void> populateDatabase(
    PollingStation pollingStation,
    PollingStationElections election,
    List<Voter> voters,
    List<Candidate> candidates,
  ) async {
    Database databaseInstance = await database;
    await databaseInstance.transaction((tsx) async {
      await GetIt.I
          .get<PollingStationDomainService>()
          .create(tsx, pollingStation);
      await GetIt.I.get<ElectionDomainService>().create(tsx, election);
      VoterDomainService voterDomainService = GetIt.I.get<VoterDomainService>();
      for (Voter voter in voters) {
        await voterDomainService.create(tsx, voter);
      }
      CandidateDomainService candidateDomainService =
          GetIt.I.get<CandidateDomainService>();
      for (Candidate candidate in candidates) {
        await candidateDomainService.create(tsx, candidate);
      }
    });
  }
}
