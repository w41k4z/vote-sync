import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:get_it/get_it.dart';
import 'package:loader_overlay/loader_overlay.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/screens/home/home_page.dart';
import 'package:vote_sync/screens/log-in/log_in_page.dart';
import 'package:vote_sync/services/api/auth_service.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/data/database_manager.dart';
import 'package:vote_sync/services/data/domain/candidate_domain_service.dart';
import 'package:vote_sync/services/data/domain/election_domain_service.dart';
import 'package:vote_sync/services/data/domain/polling_station_domain_service.dart';
import 'package:vote_sync/services/data/domain/voter_domain_service.dart';
import 'package:vote_sync/services/local_storage_service.dart';
import 'package:vote_sync/services/location_service.dart';
import 'package:vote_sync/services/secure_storage_service.dart';
import 'package:vote_sync/services/token_service.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';
import 'package:path_provider/path_provider.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await setUpServices();
  await setUpDomainServices();
  await setUpDatabase();
  await setUpAppAccess();
  runApp(const App());
}

Future<void> setUpServices() async {
  GetIt.I.registerSingleton<SecureStorageService>(SecureStorageService());
  GetIt.I.registerSingleton<TokenService>(TokenService());
  GetIt.I.registerSingleton<AuthService>(AuthService());
  GetIt.I.registerSingleton<LocationService>(LocationService());
  GetIt.I.registerSingleton<PollingStationService>(PollingStationService());
  GetIt.I.registerSingleton<LocalStorageService>(
      LocalStorageService(appDocDir: await getApplicationDocumentsDirectory()));
}

Future<void> setUpDomainServices() async {
  GetIt.I.registerSingleton<PollingStationDomainService>(
      PollingStationDomainService());
  GetIt.I.registerSingleton<VoterDomainService>(VoterDomainService());
  GetIt.I.registerSingleton<CandidateDomainService>(CandidateDomainService());
  GetIt.I.registerSingleton<ElectionDomainService>(ElectionDomainService());
}

Future<void> setUpDatabase() async {
  DatabaseManager databaseManager =
      DatabaseManager(database: await DatabaseManager.initDatabase());
  GetIt.I.registerSingleton<DatabaseManager>(databaseManager);
}

Future<void> setUpAppAccess() async {
  TokenService tokenService = GetIt.I.get<TokenService>();
  SecureStorageService secureStorageService =
      GetIt.I.get<SecureStorageService>();
  String? accessToken = await tokenService.getToken();
  String? pollingStationId =
      await secureStorageService.read('pollingStationId');
  String? electionId = await secureStorageService.read('electionId');
  GetIt.I.registerSingleton<AppInstance>(
      AppInstance(accessToken, pollingStationId, electionId));
}

class App extends StatelessWidget {
  const App({super.key});

  @override
  Widget build(BuildContext context) {
    AppInstance appInstance = GetIt.I.get<AppInstance>();
    bool hasAccess = appInstance.isUserAuthenticated() &&
        appInstance.isPollingStationIdSet() &&
        appInstance.isElectionIdSet();
    final pageDestination = hasAccess ? const HomePage() : const LogInPage();
    return GlobalLoaderOverlay(
      overlayWidgetBuilder: (_) {
        return const Center(
          child: SpinKitWaveSpinner(
            size: 75.0,
            color: Colors.white,
            waveColor: AppColors.primaryGreen,
          ),
        );
      },
      child: MaterialApp(
        title: 'VoteSync',
        theme: ThemeData(
          primaryColor: AppColors.primaryGreen,
          colorScheme: const ColorScheme.light(
            primary: AppColors.primaryGreen,
            secondary: AppColors.secondaryWhite,
          ),
          scaffoldBackgroundColor: AppColors.backgroundColor,
          appBarTheme: const AppBarTheme(
            backgroundColor: AppColors.primaryGreen,
            foregroundColor: AppColors.backgroundColor,
          ),
          bottomNavigationBarTheme: BottomNavigationBarThemeData(
            type: BottomNavigationBarType.fixed,
            selectedItemColor: Colors.white,
            unselectedItemColor: Colors.grey[400],
            backgroundColor: Colors.white,
          ),
          textSelectionTheme: const TextSelectionThemeData(
            cursorColor: Colors.white,
            selectionColor: Colors.grey,
            selectionHandleColor: Colors.white,
          ),
        ),
        home: pageDestination,
      ),
    );
  }
}
