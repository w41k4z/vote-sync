import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:sn_progress_dialog/sn_progress_dialog.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/models/voter.dart';
import 'package:vote_sync/screens/voters/registered/widgets/registered_voters_page_content.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/database_manager.dart';
import 'package:vote_sync/services/repository/voter_repository_service.dart';
import 'package:vote_sync/widgets/error/global_error_handler.dart';
import 'package:vote_sync/widgets/error/snack_bar_error.dart';

class RegisteredVotersPage extends StatefulWidget {
  const RegisteredVotersPage({super.key});

  @override
  State<RegisteredVotersPage> createState() => RegisteredVotersPageState();
}

class RegisteredVotersPageState extends State<RegisteredVotersPage> {
  List<Voter> voters = [];
  String nicFilter = '';
  int currentPage = 1;
  int totalPages = 1;
  bool hasUnsyncedVoters = false;

  int totalItems = 10; // Total items to sync
  int syncedItems = 0;

  @override
  void initState() {
    super.initState();
    _filter(currentPage, nicFilter);
  }

  @override
  Widget build(BuildContext context) {
    return RegisteredVotersPageContentWidget(
      voters: voters,
      currentPage: currentPage,
      totalPages: totalPages,
      nicFilter: nicFilter,
      hasUnsyncedVoters: hasUnsyncedVoters,
      filter: _filter,
      unregister: _unregister,
      syncButtonWidget: _syncButton(),
    );
  }

  Future<void> _filter(int page, String nic) async {
    AppInstance appInstance = GetIt.I.get<AppInstance>();
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    VoterRepositoryService voterRepositoryService =
        GetIt.I.get<VoterRepositoryService>();
    Map<String, dynamic> result =
        await voterRepositoryService.findRegisteredVoters(
      database: databaseInstance,
      pollingStationId: appInstance.getPollingStationId(),
      electionId: appInstance.getElectionId(),
      condition: ">=",
      hasVoted: 10,
      page: page,
      nic: nic,
    );
    List<Voter> registeredVoters = result['voters'];
    int pages = result['totalPages'];
    bool hasUnsynced = await voterRepositoryService.hasUnsyncedVoters(
        database: databaseInstance);
    setState(() {
      currentPage = page;
      voters = registeredVoters;
      totalPages = pages;
      nicFilter = nic;
      hasUnsyncedVoters = hasUnsynced;
    });
  }

  void _unregister(Voter voter) async {
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    VoterRepositoryService voterRepositoryService =
        GetIt.I.get<VoterRepositoryService>();
    await voterRepositoryService.unregister(
        database: databaseInstance, voter: voter);
    voters.remove(voter);
    bool hasUnsynced = await voterRepositoryService.hasUnsyncedVoters(
        database: databaseInstance);
    setState(() {
      hasUnsyncedVoters = hasUnsynced;
    });
  }

  void _syncRegisteredVoters() async {
    String condition = "=";
    int hasVoted = 10;
    ProgressDialog progressDialog = ProgressDialog(context: context);
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    List<int> totalPagesAndRows =
        await GetIt.I.get<VoterRepositoryService>().totalPagesAndRows(
              database: databaseInstance,
              condition: condition,
              hasVoted: hasVoted,
            );
    int totalPages = totalPagesAndRows[0];
    int totalRows = totalPagesAndRows[1];
    progressDialog.show(
      max: totalRows,
      msg: 'Synchronisation en cours...',
      completed: Completed(
        completedMsg: 'Synchronisation terminée',
      ),
      barrierColor: Colors.black.withOpacity(0.5),
      backgroundColor: AppColors.cardGreyBackground,
      progressValueColor: AppColors.primaryGreen,
      progressBgColor: Colors.grey,
      progressType: ProgressType.valuable,
      valueFontWeight: FontWeight.bold,
    );
    AppInstance appInstance = GetIt.I.get<AppInstance>();
    PollingStationService pollingStationService =
        GetIt.I.get<PollingStationService>();
    VoterRepositoryService voterRepositoryService =
        GetIt.I.get<VoterRepositoryService>();
    int synced = 0;
    int errors = 0;
    int updated = 0;
    for (int i = 1; i <= totalPages; i++) {
      Map<String, dynamic> result =
          await voterRepositoryService.findRegisteredVoters(
        database: databaseInstance,
        pollingStationId: appInstance.getPollingStationId(),
        electionId: appInstance.getElectionId(),
        condition: condition,
        hasVoted: hasVoted,
        size: 10,
        page: i,
      );
      List<Voter> votersChunk = result['voters'];
      try {
        await pollingStationService.registerVoters(votersChunk);
        voterRepositoryService.syncVoters(
          database: databaseInstance,
          voters: votersChunk,
        );
        if (updated == voters.length) continue;
        for (Voter updatedVoter in votersChunk) {
          int index = voters.indexWhere((voter) => voter.id == updatedVoter.id);
          if (index != -1) {
            voters[index].hasVoted = 20;
            updated++;
          }
        }
      } on DioException catch (e) {
        if (!mounted) return;
        progressDialog.close();
        if (e.type == DioExceptionType.connectionError) {
          GlobalErrorHandler.internetAccessErrorDialog(
            context: context,
            onRetry: _syncRegisteredVoters,
          );
        } else {
          SnackBarError.show(
            context: context,
            message: e.response?.data["message"],
          );
        }
        return;
      } catch (e) {
        errors += votersChunk.length;
      }
      synced += votersChunk.length;
      progressDialog.update(value: synced);
    }
    if (errors > 0 && mounted) {
      final data = errors > 1 ? 'données' : 'donné';
      SnackBarError.show(
        context: context,
        message:
            'Erreur lors de la synchronisation. $errors $data non synchronisés.',
      );
    }
    setState(() {
      hasUnsyncedVoters = false;
    });
  }

  Widget _syncButton() {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 10),
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8),
          ),
          backgroundColor: AppColors.primaryGreen,
          padding: const EdgeInsets.all(8.0),
        ),
        onPressed: () {
          showDialog(
            context: context,
            builder: (context) {
              return _syncAlertDialog(context);
            },
          );
        },
        child: const Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.sync, size: 28, color: Colors.white),
            SizedBox(width: 10), // Spacing between icon and text
            Text(
              'Tout synchroniser',
              style: TextStyle(color: Colors.white, fontSize: 16),
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }

  Widget _syncAlertDialog(BuildContext alertContext) {
    return AlertDialog(
      title: const Text('Synchronisation des données'),
      content: const Text(
          'Voulez-vous vraiment synchroniser les données? Les données synchronisées ne pourront plus être modifiées.'),
      actions: [
        TextButton(
          onPressed: () {
            Navigator.of(alertContext).pop();
          },
          child: const Text('Annuler'),
        ),
        TextButton(
          onPressed: () {
            Navigator.of(alertContext).pop();
            _syncRegisteredVoters();
          },
          child: const Text(
            'Synchroniser',
            style: TextStyle(
              color: AppColors.primaryGreen,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
      ],
    );
  }
}
