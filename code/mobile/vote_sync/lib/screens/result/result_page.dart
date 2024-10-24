import 'package:flutter/material.dart';
import 'package:vote_sync/config/pages.dart';
import 'package:vote_sync/widgets/app_drawer.dart';
import 'package:vote_sync/widgets/copyright.dart';

class ResultPage extends StatelessWidget {
  const ResultPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Résultats'),
      ),
      drawer: const AppDrawer(activeItem: Pages.RESULT),
      bottomSheet: const Copyright(),
      body: const Center(
        child: Text('Résultats'),
      ),
    );
  }
}
