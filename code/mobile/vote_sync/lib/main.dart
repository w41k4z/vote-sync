import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:get_it/get_it.dart';
import 'package:loader_overlay/loader_overlay.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/screens/log-in/log_in_page.dart';
import 'package:vote_sync/services/api/auth_service.dart';
import 'package:vote_sync/services/location_service.dart';
import 'package:vote_sync/services/token_service.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';

void main() {
  setUpServiceLocator();
  runApp(const App());
}

void setUpServiceLocator() {
  GetIt.I.registerSingleton<TokenService>(TokenService());
  GetIt.I.registerSingleton<AuthService>(AuthService());
  GetIt.I.registerSingleton<LocationService>(LocationService());
  GetIt.I.registerSingleton<PollingStationService>(PollingStationService());
}

class App extends StatelessWidget {
  const App({super.key});

  @override
  Widget build(BuildContext context) {
    return GlobalLoaderOverlay(
      overlayWidgetBuilder: (_) {
        return const Center(
          child: SpinKitWaveSpinner(
            size: 75.0,
            color: AppColors.primaryGreen,
            waveColor: AppColors.primaryGreen,
          ),
        );
      },
      child: MaterialApp(
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
        home: const LogInPage(),
      ),
    );
  }
}
