import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:vote_sync/models/candidate.dart';
import 'package:vote_sync/models/polling_station.dart';
import 'package:vote_sync/models/election.dart';
import 'package:vote_sync/models/voter.dart';
import 'package:vote_sync/services/api/api_call_service.dart';
import 'package:vote_sync/services/repository/candidate_repository_service.dart';
import 'package:vote_sync/services/repository/election_repository_service.dart';
import 'package:vote_sync/services/repository/polling_station_repository_service.dart';
import 'package:vote_sync/services/repository/voter_repository_service.dart';
import 'package:vote_sync/config/init/database_index_init.dart';
import 'package:vote_sync/config/init/database_table_init.dart';

class DatabaseManager {
  static const _databaseVersion = 1;
  static const _databaseName = 'vote_sync.db';
  final Database database;

  const DatabaseManager({required this.database});

  static Future<Database> initDatabase() async {
    String path = join(await getDatabasesPath(), _databaseName);
    return openDatabase(
      path,
      version: _databaseVersion,
      onCreate: (db, version) async {
        // Tables
        await db.execute(DatabaseTableInit.CREATE_POLLING_STATION_TABLE);
        await db.execute(
          DatabaseTableInit.CREATE_POLLING_STATION_ELECTION_TABLE,
        );
        await db.execute(DatabaseTableInit.CREATE_VOTER_TABLE);
        await db.execute(DatabaseTableInit.CREATE_CANDIDATE_TABLE);
        // Indexes
        await db.execute(DatabaseIndexInit.CREATE_VOTERS_NAME_IDX);
        await db.execute(DatabaseIndexInit.CREATE_VOTERS_FIRST_NAME_IDX);
        await db.execute(DatabaseIndexInit.CREATE_VOTERS_HAS_VOTE_IDX);
      },
    );
  }

  Future<bool> isDatabasePopulated(int pollingStationId, int electionId) async {
    PollingStationRepositoryService pollingStationRepositoryService =
        GetIt.I.get<PollingStationRepositoryService>();
    ElectionRepositoryService electionRepositoryService =
        GetIt.I.get<ElectionRepositoryService>();

    PollingStation? pollingStation = await pollingStationRepositoryService
        .findById(database, pollingStationId);
    Election? election =
        await electionRepositoryService.findById(database, electionId);
    return pollingStation != null && election != null;
  }

  Future<void> populateDatabase(
    PollingStation pollingStation,
    Election election,
    List<Voter> voters,
    List<Candidate> candidates,
  ) async {
    await database.transaction((tsx) async {
      await _persistPollingStation(tsx, pollingStation);
      await _persistElection(tsx, election);
      await _persistVoters(tsx, voters);
      await _persistCandidates(tsx, candidates);
    });
  }

  Future<void> _persistPollingStation(
      Transaction tsx, PollingStation pollingStation) async {
    await GetIt.I
        .get<PollingStationRepositoryService>()
        .create(tsx, pollingStation);
  }

  Future<void> _persistElection(Transaction tsx, Election election) async {
    await GetIt.I.get<ElectionRepositoryService>().create(tsx, election);
  }

  Future<void> _persistVoters(Transaction tsx, List<Voter> voters) async {
    VoterRepositoryService voterRepositoryService =
        GetIt.I.get<VoterRepositoryService>();
    for (Voter voter in voters) {
      await voterRepositoryService.create(tsx, voter);
    }
  }

  Future<void> _persistCandidates(
      Transaction tsx, List<Candidate> candidates) async {
    ApiCallService callService = ApiCallService();
    CandidateRepositoryService candidateRepositoryService =
        GetIt.I.get<CandidateRepositoryService>();
    for (Candidate candidate in candidates) {
      await candidate.downloadCandidateImage(callService);
      await candidateRepositoryService.create(tsx, candidate);
    }
  }
}
