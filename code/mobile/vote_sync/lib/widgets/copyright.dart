import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class Copyright extends StatelessWidget {
  static const double height = 35;

  const Copyright({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      color: AppColors.primaryGreen,
      height: height,
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
