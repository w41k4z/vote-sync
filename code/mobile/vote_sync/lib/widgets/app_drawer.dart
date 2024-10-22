import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/config/page_content.dart';
import 'package:vote_sync/screens/candidates/candidate_page.dart';
import 'package:vote_sync/screens/home/home_page.dart';
import 'package:vote_sync/screens/log-in/log_in_page.dart';
import 'package:vote_sync/screens/result/result_page.dart';
import 'package:vote_sync/screens/voters/recorded_voters_page.dart';
import 'package:vote_sync/screens/voters/voters_turnout_page.dart';
import 'package:vote_sync/services/app_instance.dart';

class AppDrawer extends StatelessWidget {
  final String activeItem;

  const AppDrawer({super.key, required this.activeItem});

  void _handleLogout(BuildContext context) async {
    await GetIt.I.get<AppInstance>().logout();
    if (!context.mounted) return;
    Navigator.of(context).pushReplacement(
      MaterialPageRoute(
        builder: (context) => const LogInPage(),
      ),
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
              color: activeItem == PageContent.HOME
                  ? AppColors.primaryGreen
                  : Colors.grey[400],
            ),
            title: Text(
              'Accueil',
              style: TextStyle(
                color: activeItem == PageContent.HOME
                    ? AppColors.primaryGreen
                    : Colors.grey[400],
              ),
            ),
            onTap: () {
              Navigator.of(context).pushReplacement(
                MaterialPageRoute(builder: (context) => const HomePage()),
              );
            },
          ),
          ListTile(
            leading: Icon(
              Icons.person_add_alt,
              color: activeItem == PageContent.REGISTERED_VOTERS
                  ? AppColors.primaryGreen
                  : Colors.grey[400],
            ),
            title: Text(
              'Liste des inscrits',
              style: TextStyle(
                color: activeItem == PageContent.REGISTERED_VOTERS
                    ? AppColors.primaryGreen
                    : Colors.grey[400],
              ),
            ),
            onTap: () {
              Navigator.of(context).pushReplacement(
                MaterialPageRoute(
                    builder: (context) => const RecordedVotersPage()),
              );
            },
          ),
          ListTile(
            leading: Icon(
              Icons.how_to_reg,
              color: activeItem == PageContent.CANDIDATES
                  ? AppColors.primaryGreen
                  : Colors.grey[400],
            ),
            title: Text(
              'Liste des candidats',
              style: TextStyle(
                color: activeItem == PageContent.CANDIDATES
                    ? AppColors.primaryGreen
                    : Colors.grey[400],
              ),
            ),
            onTap: () {
              Navigator.of(context).pushReplacement(
                MaterialPageRoute(builder: (context) => const CandidatePage()),
              );
            },
          ),
          ListTile(
            leading: Icon(
              Icons.fact_check,
              color: activeItem == PageContent.VOTERS_TURNOUT
                  ? AppColors.primaryGreen
                  : Colors.grey[400],
            ),
            title: Text(
              'Enregistrement',
              style: TextStyle(
                color: activeItem == PageContent.VOTERS_TURNOUT
                    ? AppColors.primaryGreen
                    : Colors.grey[400],
              ),
            ),
            onTap: () {
              Navigator.of(context).pushReplacement(
                MaterialPageRoute(
                    builder: (context) => const VotersTurnoutPage()),
              );
            },
          ),
          ListTile(
            leading: Icon(
              Icons.bar_chart,
              color: activeItem == PageContent.RESULT
                  ? AppColors.primaryGreen
                  : Colors.grey[400],
            ),
            title: Text(
              "Résultat",
              style: TextStyle(
                color: activeItem == PageContent.RESULT
                    ? AppColors.primaryGreen
                    : Colors.grey[400],
              ),
            ),
            onTap: () {
              Navigator.of(context).pushReplacement(
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
