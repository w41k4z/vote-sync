import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/models/polling_station.dart';
import 'package:vote_sync/models/polling_station_election.dart';
import 'package:vote_sync/screens/home/home_page_card_info.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/data/database_manager.dart';
import 'package:vote_sync/services/data/domain/election_domain_service.dart';
import 'package:vote_sync/services/data/domain/polling_station_domain_service.dart';
import 'package:vote_sync/widgets/app_drawer.dart';
import 'package:vote_sync/widgets/copyright.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  PollingStation? pollingStation;
  PollingStationElections? election;

  @override
  void initState() {
    super.initState();
    _getPollingStationAndElection();
  }

  Future<void> _getPollingStationAndElection() async {
    final appInstance = GetIt.I.get<AppInstance>();
    Database database = await GetIt.I.get<DatabaseManager>().database;
    String pollingStationId = appInstance.getPollingStationId();
    String electionId = appInstance.getElectionId();
    final storedPollingStation = await GetIt.I
        .get<PollingStationDomainService>()
        .findById(database, int.parse(pollingStationId));
    final storedElection = await GetIt.I
        .get<ElectionDomainService>()
        .findById(database, int.parse(electionId));
    setState(() {
      pollingStation = storedPollingStation;
      election = storedElection;
    });
  }

  @override
  Widget build(BuildContext context) {
    double bottomSheetHeight = Copyright.height;
    return Scaffold(
      appBar: AppBar(title: const Text('Accueil')),
      bottomSheet: const Copyright(),
      drawer: const AppDrawer(),
      backgroundColor: AppColors.neutralBackgroundColor,
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Column(
            children: [
              // Information election
              HomePageCardInfo(
                iconData: Icons.how_to_vote,
                title: "${election?.name}",
                content: [
                  "Type: ${election?.electionType}",
                  "Date: ${election?.electionDate}"
                ],
              ),

              // Participants (Candidats et electeurs)
              HomePageCardInfo(
                iconData: Icons.person_pin_rounded,
                title: "Candidats",
                content: ["${election?.candidates} candidats"],
              ),

              HomePageCardInfo(
                iconData: Icons.people,
                title: "Electeurs",
                content: ["${election?.registeredVoters} électeurs"],
              ),

              // Bureau de vote
              HomePageCardInfo(
                iconData: Icons.location_on,
                title: "Bureau de vote",
                content: ["${pollingStation?.name}"],
              ),

              // Fokontany
              HomePageCardInfo(
                iconData: Icons.home,
                title: "Fokontany",
                content: ["${pollingStation?.fokontany}"],
              ),

              // Commune
              HomePageCardInfo(
                iconData: Icons.map_outlined,
                title: "Commune",
                content: ["${pollingStation?.commune}"],
              ),

              // District
              HomePageCardInfo(
                iconData: Icons.location_searching_sharp,
                title: "District",
                content: ["${pollingStation?.district}"],
              ),

              // Region
              HomePageCardInfo(
                iconData: Icons.public,
                title: "Région",
                content: ["${pollingStation?.region}"],
              ),

              SizedBox(height: bottomSheetHeight),
            ],
          ),
        ),
      ),
    );
  }
}
