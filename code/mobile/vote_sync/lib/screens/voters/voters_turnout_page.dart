import 'package:flutter/material.dart';
import 'package:vote_sync/config/page_content.dart';
import 'package:vote_sync/widgets/app_drawer.dart';
import 'package:vote_sync/widgets/copyright.dart';

class VotersTurnoutPage extends StatelessWidget {
  const VotersTurnoutPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Electeurs enregistrés'),
      ),
      drawer: const AppDrawer(activeItem: PageContent.VOTERS_TURNOUT),
      bottomSheet: const Copyright(),
      body: const Center(
        child: Text('Electeurs enregistrés'),
      ),
    );
  }
}
