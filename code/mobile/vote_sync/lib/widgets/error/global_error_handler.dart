import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class GlobalErrorHandler {
  static Widget internetAccessErrorDialog(
      {required BuildContext context, required Function onRetry}) {
    return PopScope(
      canPop: false,
      child: AlertDialog(
        backgroundColor: AppColors.redDanger,
        icon: const Icon(
          Icons.wifi_off,
          color: Colors.white,
          size: 45,
        ),
        content: const Text(
          'Connexion à internet impossible. Veuillez vérifier votre connexion et réessayer',
          style: TextStyle(
            fontSize: 17,
            color: Colors.white,
          ),
          textAlign: TextAlign.center,
        ),
        actions: <Widget>[
          TextButton.icon(
            style: TextButton.styleFrom(
              backgroundColor: AppColors.secondaryWhite,
              foregroundColor: AppColors.redDanger,
            ),
            icon: const Icon(
              Icons.refresh,
              color: AppColors.redDanger,
            ),
            label: const Text(
              'Réessayer',
              style: TextStyle(color: AppColors.redDanger),
            ),
            onPressed: () {
              Navigator.of(context).pop();
              onRetry();
            },
          ),
        ],
      ),
    );
  }
}
