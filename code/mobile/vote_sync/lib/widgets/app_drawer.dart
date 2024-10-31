import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/config/pages.dart';
import 'package:vote_sync/screens/home/home_page.dart';
import 'package:vote_sync/screens/log-in/log_in_page.dart';
import 'package:vote_sync/screens/result/result_page.dart';
import 'package:vote_sync/screens/voters/recorded_voters_page.dart';
import 'package:vote_sync/screens/voters/registered/registered_voters_page.dart';
import 'package:vote_sync/services/app_instance.dart';

class AppDrawer extends StatelessWidget {
  final String activeItem;

  const AppDrawer({super.key, required this.activeItem});

  void _handleLogout(BuildContext context) async {
    await GetIt.I.get<AppInstance>().logout();
    if (!context.mounted) return;
    Navigator.of(context).pushAndRemoveUntil(
      MaterialPageRoute(
        builder: (context) => const LogInPage(),
      ),
      (Route<dynamic> route) => false,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      backgroundColor: AppColors.backgroundColor,
      child: Column(
        children: [
          const DrawerHeader(
            decoration: BoxDecoration(
              color: AppColors.primaryGreen,
              image: DecorationImage(
                image: AssetImage('assets/images/logo.png'),
              ),
            ),
            child: null,
          ),
          ListTile(
            leading: Icon(
              Icons.home,
              color: activeItem == Pages.HOME
                  ? AppColors.primaryGreen
                  : Colors.grey[400],
            ),
            title: Text(
              'Accueil',
              style: TextStyle(
                color: activeItem == Pages.HOME
                    ? AppColors.primaryGreen
                    : Colors.grey[400],
              ),
            ),
            onTap: () {
              Navigator.of(context).push(
                MaterialPageRoute(builder: (context) => const HomePage()),
              );
            },
          ),
          ListTile(
            leading: Icon(
              Icons.person_add_alt,
              color: activeItem == Pages.RECORDED_VOTERS
                  ? AppColors.primaryGreen
                  : Colors.grey[400],
            ),
            title: Text(
              'Liste des inscrits',
              style: TextStyle(
                color: activeItem == Pages.RECORDED_VOTERS
                    ? AppColors.primaryGreen
                    : Colors.grey[400],
              ),
            ),
            onTap: () {
              Navigator.of(context).push(
                MaterialPageRoute(
                    builder: (context) => const RecordedVotersPage()),
              );
            },
          ),
          ListTile(
            leading: Icon(
              Icons.fact_check,
              color: activeItem == Pages.REGISTERED_VOTERS
                  ? AppColors.primaryGreen
                  : Colors.grey[400],
            ),
            title: Text(
              'Enregistrement',
              style: TextStyle(
                color: activeItem == Pages.REGISTERED_VOTERS
                    ? AppColors.primaryGreen
                    : Colors.grey[400],
              ),
            ),
            onTap: () {
              Navigator.of(context).push(
                MaterialPageRoute(
                    builder: (context) => const RegisteredVotersPage()),
              );
            },
          ),
          ListTile(
            leading: Icon(
              Icons.bar_chart,
              color: activeItem == Pages.RESULT
                  ? AppColors.primaryGreen
                  : Colors.grey[400],
            ),
            title: Text(
              "Résultat",
              style: TextStyle(
                color: activeItem == Pages.RESULT
                    ? AppColors.primaryGreen
                    : Colors.grey[400],
              ),
            ),
            onTap: () {
              Navigator.of(context).push(
                MaterialPageRoute(builder: (context) => const ResultPage()),
              );
            },
          ),
          // Push the following items to the bottom
          Expanded(
            child: Container(),
          ),
          const Divider(
            color: Color.fromARGB(255, 219, 223, 219),
          ),
          ListTile(
            leading: const Icon(
              Icons.logout,
              color: AppColors.redDanger,
            ),
            title: const Text('Se deconnecter'),
            trailing: const Icon(
              Icons.directions_run_rounded,
              color: AppColors.redDanger,
            ),
            textColor: AppColors.redDanger,
            onTap: () {
              _handleLogout(context);
            },
          ),
        ],
      ),
    );
  }
}
