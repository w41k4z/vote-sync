import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class GlobalErrorHandler {
  static void internetAccessErrorDialog(
      {required BuildContext context, required Function onRetry}) {
    showDialog(
        context: context,
        builder: (BuildContext buildContext) {
          return PopScope(
            canPop: false,
            child: AlertDialog(
              icon: const Icon(
                Icons.wifi_off,
                color: AppColors.redDanger,
                size: 45,
              ),
              content: const Text(
                'Connexion à internet impossible. Veuillez vérifier votre connexion et réessayer',
                style: TextStyle(
                  fontSize: 17,
                  color: Colors.black,
                ),
                textAlign: TextAlign.center,
              ),
              actions: <Widget>[
                TextButton.icon(
                  style: TextButton.styleFrom(
                    side: const BorderSide(
                      color: AppColors.redDanger,
                    ),
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
                    Navigator.of(buildContext).pop();
                    onRetry();
                  },
                ),
              ],
            ),
          );
        });
  }
}
