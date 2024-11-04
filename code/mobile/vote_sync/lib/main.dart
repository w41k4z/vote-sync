import 'package:flutter/material.dart';

import 'package:get_it/get_it.dart';

import 'package:loader_overlay/loader_overlay.dart';

import 'package:flutter_native_splash/flutter_native_splash.dart';

import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/screens/home/home_page.dart';
import 'package:vote_sync/screens/log-in/log_in_page.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/set_up.dart';
import 'package:vote_sync/widgets/loading_spinner.dart';

void main() async {
  WidgetsBinding widgetsBinding = WidgetsFlutterBinding.ensureInitialized();
  FlutterNativeSplash.preserve(widgetsBinding: widgetsBinding);
  await SetUp.setUpApp();
  FlutterNativeSplash.remove();
  runApp(const App());
}

class App extends StatefulWidget {
  const App({super.key});

  @override
  State<App> createState() => _AppState();
}

class _AppState extends State<App> {
  @override
  Widget build(BuildContext context) {
    AppInstance appInstance = GetIt.I.get<AppInstance>();
    final pageDestination =
        appInstance.hasAccess() ? const HomePage() : const LogInPage();
    return GlobalLoaderOverlay(
      overlayWidgetBuilder: (_) {
        return const LoadingSpinner();
      },
      child: MaterialApp(
        title: 'VoteSync',
        debugShowCheckedModeBanner: false,
        theme: _appThemeData(),
        home: pageDestination,
      ),
    );
  }

  ThemeData _appThemeData() {
    return ThemeData(
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
        cursorColor: AppColors.primaryGreen,
        selectionColor: Colors.grey,
        selectionHandleColor: AppColors.primaryGreen,
      ),
    );
  }
}
