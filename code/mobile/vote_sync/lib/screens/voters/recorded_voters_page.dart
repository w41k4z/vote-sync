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

class RecordedVotersPage extends StatefulWidget {
  const RecordedVotersPage({super.key});

  @override
  State<RecordedVotersPage> createState() => _RecordedVotersPageState();
}

class _RecordedVotersPageState extends State<RecordedVotersPage> {
  List<Voter> voters = [];
  String nicFilter = '';
  int currentPage = 1;
  int totalPages = 1;

  @override
  void initState() {
    super.initState();
    _filter(currentPage, nicFilter);
  }

  Future<void> _filter(int page, String nic) async {
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    Map<String, dynamic> result = await GetIt.I
        .get<VoterRepositoryService>()
        .findRegisteredVoters(database: databaseInstance, page: page, nic: nic);
    List<Voter> registeredVoters = result['voters'];
    int pages = result['totalPages'];
    setState(() {
      currentPage = page;
      voters = registeredVoters;
      totalPages = pages;
      nicFilter = nic;
    });
  }

  Future<void> _register(int index) async {
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    await GetIt.I
        .get<VoterRepositoryService>()
        .register(database: databaseInstance, voter: voters[index]);
    setState(() {
      voters.removeAt(index);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Electeurs inscrits'),
      ),
      drawer: const AppDrawer(activeItem: Pages.REGISTERED_VOTERS),
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
            trailing: ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColors.primaryGreen,
                foregroundColor: Colors.white,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
              ),
              onPressed: () async {
                await _register(index);
              },
              child: const Text('Enregistrer'),
            ),
          ),
        );
      },
    );
  }
}
