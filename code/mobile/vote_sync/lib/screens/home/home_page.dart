import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/screens/home/home_page_card_info.dart';
import 'package:vote_sync/widgets/app_drawer.dart';
import 'package:vote_sync/widgets/copyright.dart';

class HomePage extends StatefulWidget {
  HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    double bottomSheetHeight = 35;
    return Scaffold(
      appBar: AppBar(title: const Text('Accueil')),
      bottomSheet: const Copyright(),
      drawer: const AppDrawer(),
      backgroundColor: AppColors.neutralBackgroundColor,
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Column(
            children: [
              // Information election
              HomePageCardInfo(
                iconData: Icons.how_to_vote,
                title: "Election municipale 2024",
                content: ["Type: Locale", "Date: 05 Décembre 2024"],
              ),

              // Participants (Candidats et electeurs)
              HomePageCardInfo(
                iconData: Icons.person_pin_rounded,
                title: "Candidats",
                content: ["5 candidats"],
              ),

              HomePageCardInfo(
                iconData: Icons.people,
                title: "Electeurs",
                content: ["10 électeurs"],
              ),

              // Bureau de vote
              HomePageCardInfo(
                iconData: Icons.location_on,
                title: "Bureau de vote",
                content: ["Lycée Technique Alarobia S10"],
              ),

              // Fokontany
              HomePageCardInfo(
                iconData: Icons.home,
                title: "Fokontany",
                content: ["Ivandry"],
              ),

              // Commune
              HomePageCardInfo(
                iconData: Icons.map_outlined,
                title: "Commune",
                content: ["4e Arrondissement"],
              ),

              // District
              HomePageCardInfo(
                iconData: Icons.location_searching_sharp,
                title: "District",
                content: ["Antananarivo IV"],
              ),

              // Region
              HomePageCardInfo(
                iconData: Icons.public,
                title: "Région",
                content: ["Analamanga"],
              ),

              SizedBox(height: bottomSheetHeight),
            ],
          ),
        ),
      ),
    );
  }
}
