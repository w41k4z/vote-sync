import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Informations')),
      body: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Nom du bureau de vote'),
            Text('Nombre de candidats inscrits'),
            Text('Nombre d\'électeurs inscrits'),
          ],
        ),
      ),
    );
  }
}
