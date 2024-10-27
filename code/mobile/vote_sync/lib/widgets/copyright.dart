import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class Copyright extends StatelessWidget {
  static const double height = 30;

  const Copyright({super.key});

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    return Container(
      color: AppColors.primaryGreen,
      height: height,
      width: screenWidth,
      child: const Center(
        child: Text(
          '© 2024 CENI Madagascar',
          style: TextStyle(
            color: Colors.white,
            fontSize: 11,
          ),
        ),
      ),
    );
  }
}
