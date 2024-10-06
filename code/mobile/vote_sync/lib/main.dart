import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:get_it/get_it.dart';
import 'package:loader_overlay/loader_overlay.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/screens/home/home_page.dart';
import 'package:vote_sync/screens/log-in/log_in_page.dart';
import 'package:vote_sync/services/api/auth_service.dart';
import 'package:vote_sync/services/data/database_manager.dart';
import 'package:vote_sync/services/location_service.dart';
import 'package:vote_sync/services/token_service.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await setUpServiceLocator();
  await setUpDatabase();
  runApp(const App());
}

Future<void> setUpServiceLocator() async {
  TokenService tokenService = TokenService();
  String? accessToken = await tokenService.getToken();
  GetIt.I.registerSingleton<TokenService>(tokenService);
  GetIt.I.registerSingleton<AuthService>(AuthService(accessToken));
  GetIt.I.registerSingleton<LocationService>(LocationService());
  GetIt.I.registerSingleton<PollingStationService>(PollingStationService());
}

Future<void> setUpDatabase() async {
  DatabaseManager databaseManager = DatabaseManager();
  // initializing the database
  await databaseManager.database;
  GetIt.I.registerSingleton<DatabaseManager>(databaseManager);
}

class App extends StatelessWidget {
  const App({super.key});

  @override
  Widget build(BuildContext context) {
    bool isAuthenticated = GetIt.I.get<AuthService>().isAuthenticated();
    final pageDestination =
        isAuthenticated ? const HomePage() : const LogInPage();
    return MaterialApp(
      title: 'Flutter Demo',
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
      home: LoaderOverlay(
        overlayWidgetBuilder: (_) {
          return const Center(
            child: SpinKitWaveSpinner(
              size: 75.0,
              color: AppColors.primaryGreen,
              waveColor: AppColors.primaryGreen,
            ),
          );
        },
        child: pageDestination,
      ),
    );
  }
}
