import 'package:flutter/material.dart';

class GlobalErrorHandler {
  static Widget internetAccessErrorDialog(
      {required BuildContext context, required Function onRetry}) {
    return AlertDialog(
      title: const Text('Accès à Internet non disponible'),
      content: const Text(
          'Connexion à l\'internet requise. Veuillez vérifier votre connexion et réessayer'),
      actions: <Widget>[
        TextButton(
          child: const Text('Réessayer'),
          onPressed: () {
            Navigator.of(context).pop();
            onRetry();
          },
        ),
      ],
    );
  }
}
