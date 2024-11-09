// ignore_for_file: non_constant_identifier_names

import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/services/database_manager.dart';
import 'package:vote_sync/services/repository/candidate_repository_service.dart';
import 'package:vote_sync/services/repository/polling_station_repository_service.dart';
import 'package:vote_sync/services/repository/polling_station_result_image_repository_service.dart';
import 'package:vote_sync/services/repository/voter_repository_service.dart';
import 'package:vote_sync/services/secure_storage_service.dart';
import 'package:vote_sync/services/token_service.dart';

class AppInstance {
  static String ACCESS_TOKEN_KEY = 'accessToken';
  static String POLLING_STATION_ID_KEY = 'pollingStationId';
  static String ELECTION_ID_KEY = 'electionId';

  String? _accessToken;
  String? _pollingStationId;
  String? _electionId;

  AppInstance(
      String? accessToken, String? pollingStationId, String? electionId) {
    _accessToken = accessToken;
    _pollingStationId = pollingStationId;
    _electionId = electionId;
  }

  bool isUserAuthenticated() {
    return _accessToken != null;
  }

  bool hasAccess() {
    return isUserAuthenticated() &&
        _pollingStationId != null &&
        _electionId != null;
  }

  Future<void> grantAccess(
      String accessToken, String pollingStationId, String electionId) async {
    await GetIt.I.get<TokenService>().saveToken(accessToken);
    await GetIt.I
        .get<SecureStorageService>()
        .save('pollingStationId', pollingStationId);
    await GetIt.I.get<SecureStorageService>().save('electionId', electionId);
    _accessToken = accessToken;
    _pollingStationId = pollingStationId;
    _electionId = electionId;
  }

  String getToken() {
    // Dart does not support to return a String even after null check
    // So care to call isUserAuthenticated() before calling getToken() to expect a non-null value
    return _accessToken ?? '';
  }

  String getPollingStationId() {
    // Dart does not support to return a String even after null check
    // So care to call isPollingStationIdSet() before calling getPollingStationId() to expect a non-null value
    return _pollingStationId ?? '';
  }

  String getElectionId() {
    // Dart does not support to return a String even after null check
    // So care to call isElectionIdSet() before calling getElectionId() to expect a non-null value
    return _electionId ?? '';
  }

  Future<void> logout() async {
    await GetIt.I.get<TokenService>().clearToken();
    _accessToken = null;
    _pollingStationId = null;
    _electionId = null;
  }

  Future<void> deleteData() async {
    Database database = GetIt.I.get<Database>();
    await database.transaction((tsx) async {
      await GetIt.I
          .get<PollingStationResultImageRepositoryService>()
          .deleteImages(
            transaction: tsx,
            electionId: _electionId!,
            pollingStationId: _pollingStationId!,
          );
      await GetIt.I.get<VoterRepositoryService>().deleteAll(
            transaction: tsx,
            electionId: _electionId!,
            pollingStationId: _pollingStationId!,
          );
      await GetIt.I.get<CandidateRepositoryService>().deleteAll(
            transaction: tsx,
            electionId: _electionId!,
            pollingStationId: _pollingStationId!,
          );
      await GetIt.I.get<PollingStationRepositoryService>().delete(
          transaction: tsx,
          pollingStationId: _pollingStationId!,
          electionId: _electionId!);
    });
    logout();
  }

  Future<void> deleteResults() async {
    Database databaseInstace = GetIt.I.get<DatabaseManager>().database;
    await databaseInstace.transaction((tsx) async {
      await GetIt.I.get<CandidateRepositoryService>().deleteResults(
            transaction: tsx,
            electionId: _electionId!,
            pollingStationId: _pollingStationId!,
          );
      await GetIt.I.get<PollingStationRepositoryService>().deleteResults(
            transaction: tsx,
            electionId: _electionId!,
            pollingStationId: _pollingStationId!,
          );
      await GetIt.I
          .get<PollingStationResultImageRepositoryService>()
          .deleteImages(
            transaction: tsx,
            electionId: _electionId!,
            pollingStationId: _pollingStationId!,
          );
    });
  }
}
