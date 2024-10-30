import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/config/pages.dart';
import 'package:vote_sync/models/candidate.dart';
import 'package:vote_sync/screens/result/widgets/candidate_votes.dart';
import 'package:vote_sync/screens/result/widgets/election_stat_result.dart';
import 'package:vote_sync/screens/result/widgets/result_bottom_navigation_bar.dart';
import 'package:vote_sync/screens/result/widgets/result_edit_modal.dart';
import 'package:vote_sync/screens/result/widgets/result_images.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/database_manager.dart';
import 'package:vote_sync/services/repository/candidate_repository_service.dart';
import 'package:vote_sync/services/local_storage_service.dart';
import 'package:vote_sync/widgets/app_drawer.dart';

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
      bottomNavigationBar: ResultBottomNavigationBar(onScanPressed: () {
        print('Scan pressed');
      }, onUploadPressed: () {
        print('Upload pressed');
      }, onEditPressed: () {
        showResultEditModal();
      }),
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
    return ElectionStatResult.widgets() +
        [
          const SizedBox(
            height: 15,
          ),
        ] +
        CandidateVotes.widgets(
          candidates: candidates,
          localStorageService: localStorageService,
        );
  }

  void showResultEditModal() {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      useSafeArea: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (context) => Padding(
        padding: EdgeInsets.only(
          bottom:
              MediaQuery.of(context).viewInsets.bottom, // Adjust for keyboard
        ),
        child: const ResultEditModal(
          nullVotes: 0,
          blankVotes: 0,
        ),
      ),
    ).then((result) {
      if (result != null) {
        print(
            'New Values: ${result['nullVotes']} and ${result['blankVotes']} and ${result['candidates']}');
        // Handle the result here, e.g., update the UI.
      }
    });
  }
}
