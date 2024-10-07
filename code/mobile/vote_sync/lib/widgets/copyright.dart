import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class Copyright extends StatelessWidget {
  const Copyright({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      color: AppColors.primaryGreen,
      height: 35,
      child: const Center(
        child: Text(
          '© 2024 CENI Madagascar',
          style: TextStyle(
            color: Colors.white,
          ),
        ),
      ),
    );
  }
}
