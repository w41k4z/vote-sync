import 'dart:io';

import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/config/pages.dart';
import 'package:vote_sync/models/candidate.dart';
import 'package:vote_sync/models/polling_station.dart';
import 'package:vote_sync/models/polling_station_result_image.dart';
import 'package:vote_sync/screens/result/widgets/candidate_votes.dart';
import 'package:vote_sync/screens/result/widgets/election_stat_result.dart';
import 'package:vote_sync/screens/result/widgets/result_bottom_navigation_bar.dart';
import 'package:vote_sync/screens/result/widgets/result_edit_modal.dart';
import 'package:vote_sync/screens/result/widgets/result_image_scanner.dart';
import 'package:vote_sync/screens/result/widgets/result_images.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/database_manager.dart';
import 'package:vote_sync/services/repository/candidate_repository_service.dart';
import 'package:vote_sync/services/local_storage_service.dart';
import 'package:vote_sync/services/repository/polling_station_repository_service.dart';
import 'package:vote_sync/services/repository/polling_station_result_image_repository_service.dart';
import 'package:vote_sync/services/repository/voter_repository_service.dart';
import 'package:vote_sync/widgets/app_drawer.dart';
import 'package:vote_sync/widgets/copyright.dart';
import 'package:vote_sync/widgets/error/snack_bar_error.dart';
import 'package:vote_sync/screens/result/widgets/qr_code_scanner_page.dart'; // Add this import

class ResultPage extends StatefulWidget {
  const ResultPage({super.key});

  @override
  State<ResultPage> createState() => _ResultPageState();
}

class _ResultPageState extends State<ResultPage> {
  PollingStation? pollingStation;
  List<PollingStationResultImage> images = [];
  int voters = 0;
  int registered = 0;
  List<Candidate> candidates = [];
  LocalStorageService localStorageService = GetIt.I.get<LocalStorageService>();
  String informationFilter = '';

  @override
  void initState() {
    super.initState();
    _getPollingStationAndImagesAndCandidates();
  }

  Future<void> _getPollingStationAndImagesAndCandidates() async {
    AppInstance appInstance = GetIt.I.get<AppInstance>();
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    PollingStation? pollingStationResult = await GetIt.I
        .get<PollingStationRepositoryService>()
        .findByIdAndElectionId(
          databaseInstance,
          appInstance.getPollingStationId(),
          appInstance.getElectionId(),
        );
    List<PollingStationResultImage> imagesResult = await GetIt.I
        .get<PollingStationResultImageRepositoryService>()
        .findAllByPollingStationIdAndElectionId(
          database: databaseInstance,
          pollingStationId: appInstance.getPollingStationId(),
          electionId: appInstance.getElectionId(),
        );
    List<Candidate> candidatesResult =
        await GetIt.I.get<CandidateRepositoryService>().findAll(
              database: databaseInstance,
              pollingStationId: appInstance.getPollingStationId(),
              electionId: appInstance.getElectionId(),
            );
    List<int> result = await GetIt.I
        .get<VoterRepositoryService>()
        .getVotersCountAndRegisteredCount(
          database: databaseInstance,
        );
    setState(() {
      pollingStation = pollingStationResult;
      candidates = candidatesResult;
      images = imagesResult;
      voters = result[0];
      registered = result[1];
    });
  }

  Future<void> _updateResult() async {
    int totalVotes = 0;
    for (Candidate candidate in candidates) {
      totalVotes += candidate.votes;
    }
    totalVotes += pollingStation!.nulls;
    totalVotes += pollingStation!.blanks;
    if (totalVotes > registered) {
      String message =
          "Le total des votes dépasse le nombre d'électeurs enregistrés.";
      SnackBarError.show(context: context, message: message);
      return;
    }
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    CandidateRepositoryService candidateRepositoryService =
        GetIt.I.get<CandidateRepositoryService>();
    for (Candidate candidate in candidates) {
      await candidateRepositoryService.updateVote(
        database: databaseInstance,
        candidate: candidate,
      );
    }
    await GetIt.I
        .get<PollingStationRepositoryService>()
        .updateNullAndBlankVotes(
          database: databaseInstance,
          pollingStation: pollingStation!,
        );
  }

  @override
  Widget build(BuildContext context) {
    bool isResultSynced = false;
    if (pollingStation != null) {
      isResultSynced = pollingStation!.isSynced();
    }
    return Scaffold(
      appBar: AppBar(
        title: const Text('Résultat'),
      ),
      drawer: const AppDrawer(activeItem: Pages.RESULT),
      bottomSheet: isResultSynced ? const Copyright() : null,
      bottomNavigationBar: isResultSynced
          ? null
          : ResultBottomNavigationBar(onScanPressed: () async {
              await _scanQrCode();
            }, onUploadPressed: () {
              print('Upload pressed');
            }, onEditPressed: () {
              _showResultEditModal();
            }),
      body: Padding(
        padding: const EdgeInsets.symmetric(vertical: 2),
        child: SingleChildScrollView(
          physics: const AlwaysScrollableScrollPhysics(),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
                  ResultImages(
                    images: images,
                  ),
                ] +
                _columnWidgets(),
          ),
        ),
      ),
    );
  }

  List<Widget> _columnWidgets() {
    return ElectionStatResult.widgets(
          pollingStation: pollingStation,
          voters: voters,
          registered: registered,
        ) +
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

  void _showResultEditModal() {
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
        child: ResultEditModal(
          nullVotes: pollingStation?.nulls ?? 0,
          blankVotes: pollingStation?.blanks ?? 0,
          candidates: candidates,
        ),
      ),
    ).then((result) async {
      if (result != null) {
        pollingStation!.nulls = result['nullVotes'];
        pollingStation!.blanks = result['blankVotes'];
        for (int i = 0; i < candidates.length; i++) {
          candidates[i].votes = result['candidates'][i];
        }
        await _updateResult();
      }
    });
  }

  Future<void> _scanQrCode() async {
    final barcode = await Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => const QrCodeScannerPage(),
      ),
    );
    print(barcode);
    if (barcode != null) {
      _scanImages();
    }
  }

  Future<void> _scanImages() async {
    final List<File>? images = await Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => const ResultImageScanner(),
      ),
    );
    if (images != null) {
      PollingStationResultImageRepositoryService
          pollingStationResultImageRepositoryService =
          GetIt.I.get<PollingStationResultImageRepositoryService>();
      List<PollingStationResultImage> pollingStationResultImages =
          await pollingStationResultImageRepositoryService.createAll(
        database: GetIt.I.get<DatabaseManager>().database,
        pollingStationId: GetIt.I.get<AppInstance>().getPollingStationId(),
        electionId: GetIt.I.get<AppInstance>().getElectionId(),
        imagesPath: images.map((image) => image.path).toList(),
      );
      setState(() {
        this.images = pollingStationResultImages;
      });
    }
  }
}
