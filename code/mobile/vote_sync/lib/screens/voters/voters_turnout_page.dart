import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/config/pages.dart';
import 'package:vote_sync/models/voter.dart';
import 'package:vote_sync/services/database_manager.dart';
import 'package:vote_sync/services/repository/voter_repository_service.dart';
import 'package:vote_sync/widgets/app_drawer.dart';
import 'package:vote_sync/widgets/copyright.dart';

class VotersTurnoutPage extends StatefulWidget {
  const VotersTurnoutPage({super.key});

  @override
  State<VotersTurnoutPage> createState() => _VotersTurnoutPageState();
}

class _VotersTurnoutPageState extends State<VotersTurnoutPage> {
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

  Future<void> _filter(int page, String nic) async {
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    VoterRepositoryService voterRepositoryService =
        GetIt.I.get<VoterRepositoryService>();
    Map<String, dynamic> result =
        await voterRepositoryService.findRegisteredVoters(
            database: databaseInstance,
            condition: ">=",
            hasVoted: 10,
            page: page,
            nic: nic);
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
    await GetIt.I
        .get<VoterRepositoryService>()
        .unregister(database: databaseInstance, voter: voter);
    voters.remove(voter);
    setState(() {});
  }

  void _syncRegisteredVoters() async {
    await showDialog(
      context: context,
      barrierDismissible: false, // Prevent dismissal until task is done
      builder: (context) {
        return StatefulBuilder(
          builder: (context, setState) {
            return AlertDialog(
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12)),
              content: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text(
                    'Syncing Items... ($syncedItems / $totalItems)',
                    style: TextStyle(fontSize: 18),
                  ),
                  const SizedBox(height: 16),
                  LinearProgressIndicator(
                    value: syncedItems / totalItems,
                    minHeight: 8,
                  ),
                ],
              ),
            );
          },
        );
      },
    );

    // Simulate the sync process
    for (int i = 1; i <= totalItems; i++) {
      await Future.delayed(Duration(seconds: 1)); // Simulate delay
      setState(() {
        syncedItems = i; // Update the synced items count
      });

      // Update the dialog's UI
      (context as Element).markNeedsBuild();
      print(i);
    }

    // Close the dialog after completion
    print("Vita");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Electeurs enregistrés'),
      ),
      drawer: const AppDrawer(activeItem: Pages.VOTERS_TURNOUT),
      bottomSheet: const Copyright(),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              onChanged: (newValue) {
                _filter(1, newValue);
              },
              decoration: InputDecoration(
                hintText: 'Rechercher par identifiant CIN',
                prefixIcon: const Icon(Icons.search),
                filled: true,
                fillColor: Colors.white,
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10.0),
                  borderSide: BorderSide.none,
                ),
              ),
            ),
          ),
          hasUnsyncedVoters
              ? const SizedBox(height: 10)
              : const SizedBox.shrink(),
          hasUnsyncedVoters ? _syncButton() : const SizedBox.shrink(),
          hasUnsyncedVoters
              ? const SizedBox(height: 10)
              : const SizedBox.shrink(),
          Expanded(
            child: voters.isEmpty
                ? const Center(
                    child: Text(
                    "Aucun résultat",
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                    ),
                  ))
                : _votersList(),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 10.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                IconButton(
                  icon: const Icon(Icons.arrow_back),
                  onPressed: () {
                    int page =
                        currentPage - 1 > 0 ? currentPage - 1 : totalPages;
                    _filter(page, nicFilter);
                  },
                ),
                Text(
                  'Page $currentPage / $totalPages',
                  style: const TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
                IconButton(
                  icon: const Icon(Icons.arrow_forward),
                  onPressed: () {
                    int page =
                        currentPage + 1 <= totalPages ? currentPage + 1 : 1;
                    _filter(page, nicFilter);
                  },
                ),
              ],
            ),
          ),
          const SizedBox(
            height: Copyright.height,
          )
        ],
      ),
    );
  }

  Widget _votersList() {
    return ListView.builder(
      itemCount: voters.length,
      itemBuilder: (context, index) {
        final voter = voters[index];
        return Card(
          margin: const EdgeInsets.symmetric(
            horizontal: 10.0,
            vertical: 5.0,
          ),
          child: ListTile(
            leading: const CircleAvatar(
              backgroundColor: AppColors.primaryGreen,
              child: Icon(
                Icons.person,
                color: AppColors.backgroundColor,
              ),
            ),
            title: Text('${voter.firstName} ${voter.name}',
                style: const TextStyle(
                  fontWeight: FontWeight.bold,
                )),
            subtitle: Text('CIN: ${voter.nic}'),
            trailing: voter.isSynchronized()
                ? IconButton(
                    icon: const Icon(
                      Icons.check_circle,
                      color: AppColors.primaryGreen,
                    ),
                    onPressed: () => {},
                  )
                : Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      const Icon(
                        Icons.sync_problem,
                        color: AppColors.redDanger,
                      ),
                      IconButton(
                        icon: const Icon(
                          Icons.delete,
                          color: AppColors.redDanger,
                        ),
                        onPressed: () => {_unregister(voter)},
                      ),
                    ],
                  ),
          ),
        );
      },
    );
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
                color: AppColors.primaryGreen, fontWeight: FontWeight.bold),
          ),
        ),
      ],
    );
  }
}
