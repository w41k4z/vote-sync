import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/screens/log-in/log_in_page.dart';
import 'package:vote_sync/services/api/auth_service.dart';

class AppDrawer extends StatelessWidget {
  const AppDrawer({super.key});

  void _handleLogout(BuildContext context) async {
    await GetIt.I.get<AuthService>().logout();
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
      child: ListView(
        padding: EdgeInsets.zero,
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
          const ListTile(
            title: Text('Home'),
            onTap: null,
          ),
          const ListTile(
            title: Text('Item 2'),
            onTap: null,
          ),
          // add a divider between the items
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
              // a person running to the right
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
