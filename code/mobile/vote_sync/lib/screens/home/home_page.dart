import 'package:flutter/material.dart';
import 'package:vote_sync/widgets/app_drawer.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Accueil')),
      drawer: const AppDrawer(),
      body: const Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text('Nom du bureau de vote'),
          Text('Nombre de candidats inscrits'),
          Text('Nombre d\'électeurs inscrits'),
        ],
      ),
    );
  }
}
