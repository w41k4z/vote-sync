import 'dart:io';

import 'package:get_it/get_it.dart';
import 'package:path_provider/path_provider.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/services/api/auth_service.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/database_manager.dart';
import 'package:vote_sync/services/local_storage_service.dart';
import 'package:vote_sync/services/location_service.dart';
import 'package:vote_sync/services/repository/candidate_repository_service.dart';
import 'package:vote_sync/services/repository/election_repository_service.dart';
import 'package:vote_sync/services/repository/polling_station_repository_service.dart';
import 'package:vote_sync/services/repository/voter_repository_service.dart';
import 'package:vote_sync/services/secure_storage_service.dart';
import 'package:vote_sync/services/token_service.dart';

class SetUp {
  static Future<void> setUpApp() async {
    await _registerServices();
    await _registerApiServices();
    await _registerRepositoryServices();
    await _initDatabase();
    await _initAppAccess();
  }

  static Future<void> _registerServices() async {
    GetIt.I.registerLazySingleton<SecureStorageService>(
      () => SecureStorageService(),
    );
    GetIt.I.registerLazySingleton<TokenService>(() => TokenService());
    GetIt.I.registerLazySingleton<LocationService>(() => LocationService());
    Directory appDocDir = await getApplicationDocumentsDirectory();
    GetIt.I.registerLazySingleton<LocalStorageService>(
      () => LocalStorageService(
        appDocDir: appDocDir,
      ),
    );
  }

  static Future<void> _registerApiServices() async {
    GetIt.I.registerLazySingleton<AuthService>(() => AuthService());
    GetIt.I.registerLazySingleton<PollingStationService>(
      () => PollingStationService(),
    );
  }

  static Future<void> _registerRepositoryServices() async {
    GetIt.I.registerLazySingleton<PollingStationRepositoryService>(
      () => PollingStationRepositoryService(),
    );
    GetIt.I.registerLazySingleton<VoterRepositoryService>(
      () => VoterRepositoryService(),
    );
    GetIt.I.registerLazySingleton<CandidateRepositoryService>(
      () => CandidateRepositoryService(),
    );
    GetIt.I.registerLazySingleton<ElectionRepositoryService>(
      () => ElectionRepositoryService(),
    );
  }

  static Future<void> _initDatabase() async {
    Database database = await DatabaseManager.initDatabase();
    GetIt.I.registerLazySingleton<DatabaseManager>(
      () => DatabaseManager(database: database),
    );
  }

  static Future<void> _initAppAccess() async {
    TokenService tokenService = GetIt.I.get<TokenService>();
    SecureStorageService secureStorageService =
        GetIt.I.get<SecureStorageService>();
    String? accessToken = await tokenService.getToken();
    String? pollingStationId =
        await secureStorageService.read(AppInstance.POLLING_STATION_ID_KEY);
    String? electionId =
        await secureStorageService.read(AppInstance.ELECTION_ID_KEY);
    GetIt.I.registerSingleton<AppInstance>(
      AppInstance(accessToken, pollingStationId, electionId),
    );
  }
}
