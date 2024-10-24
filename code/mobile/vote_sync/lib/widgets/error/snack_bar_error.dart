import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class SnackBarError {
  static void show({required BuildContext context, required String message}) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        duration: const Duration(seconds: 5),
        backgroundColor: AppColors.secondaryWhite,
        content: Center(
          child: Text(
            message,
            style: const TextStyle(color: AppColors.redDanger),
          ),
        ),
      ),
    );
  }
}
