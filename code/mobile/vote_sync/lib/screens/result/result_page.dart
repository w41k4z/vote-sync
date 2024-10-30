import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/config/pages.dart';
import 'package:vote_sync/models/candidate.dart';
import 'package:vote_sync/screens/result/widgets/candidate_votes.dart';
import 'package:vote_sync/screens/result/widgets/election_stat_result.dart';
import 'package:vote_sync/screens/result/widgets/result_images.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/database_manager.dart';
import 'package:vote_sync/services/repository/candidate_repository_service.dart';
import 'package:vote_sync/services/local_storage_service.dart';
import 'package:vote_sync/widgets/app_drawer.dart';
import 'package:vote_sync/widgets/copyright.dart';

class ResultPage extends StatefulWidget {
  const ResultPage({super.key});

  @override
  State<ResultPage> createState() => _ResultPageState();
}

class _ResultPageState extends State<ResultPage> {
  List<Candidate> candidates = [];
  LocalStorageService localStorageService = GetIt.I.get<LocalStorageService>();
  String informationFilter = '';

  @override
  void initState() {
    super.initState();
    _getCandidates();
  }

  Future<void> _getCandidates() async {
    AppInstance appInstance = GetIt.I.get<AppInstance>();
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    List<Candidate> result =
        await GetIt.I.get<CandidateRepositoryService>().findAll(
              database: databaseInstance,
              pollingStationId: appInstance.getPollingStationId(),
              electionId: appInstance.getElectionId(),
            );
    setState(() {
      candidates = result;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Résultat'),
      ),
      drawer: const AppDrawer(activeItem: Pages.RESULT),
      bottomSheet: const Copyright(),
      body: Padding(
        padding: const EdgeInsets.symmetric(vertical: 2),
        child: SingleChildScrollView(
          physics: const AlwaysScrollableScrollPhysics(),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
                  const ResultImages(),
                ] +
                _columnWidgets(),
          ),
        ),
      ),
    );
  }

  List<Widget> _columnWidgets() {
    double bottomSheetHeight = Copyright.height;
    return ElectionStatResult.widgets() +
        [
          const SizedBox(
            height: 15,
          ),
        ] +
        CandidateVotes.widgets(
          candidates: candidates,
          localStorageService: localStorageService,
        ) +
        [
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 10),
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(8),
                ),
                backgroundColor: AppColors.primaryGreen,
                padding: const EdgeInsets.all(8.0),
              ),
              onPressed: () {},
              child: const Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(Icons.sync, size: 28, color: Colors.white),
                  SizedBox(width: 10), // Spacing between icon and text
                  Text(
                    'Synchroniser les données',
                    style: TextStyle(color: Colors.white, fontSize: 16),
                    textAlign: TextAlign.center,
                  ),
                ],
              ),
            ),
          ),
          SizedBox(height: bottomSheetHeight + 10),
        ];
  }
}
