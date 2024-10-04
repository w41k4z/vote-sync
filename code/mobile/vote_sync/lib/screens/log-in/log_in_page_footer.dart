import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class LogInPageFooter extends StatelessWidget {
  const LogInPageFooter({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      color: AppColors.primaryGreen,
      child: const Column(
        children: [
          // Copyright text
          Text(
            '© 2024 CENI Madagascar',
            style: TextStyle(color: Colors.white),
          ),
          SizedBox(height: 20),
        ],
      ),
    );
  }
}
