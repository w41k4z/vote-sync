import 'dart:io';

import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/config/pages.dart';
import 'package:vote_sync/models/candidate.dart';
import 'package:vote_sync/services/database_manager.dart';
import 'package:vote_sync/services/repository/candidate_repository_service.dart';
import 'package:vote_sync/services/local_storage_service.dart';
import 'package:vote_sync/widgets/app_drawer.dart';
import 'package:vote_sync/widgets/copyright.dart';

class CandidatePage extends StatefulWidget {
  const CandidatePage({super.key});

  @override
  State<CandidatePage> createState() => _CandidatePageState();
}

class _CandidatePageState extends State<CandidatePage> {
  List<Candidate> candidates = [];
  LocalStorageService localStorageService = GetIt.I.get<LocalStorageService>();
  String informationFilter = '';

  @override
  void initState() {
    super.initState();
    _filter(informationFilter);
  }

  Future<void> _filter(String newInformationFilter) async {
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    List<Candidate> result = await GetIt.I
        .get<CandidateRepositoryService>()
        .findAll(databaseInstance, newInformationFilter);
    setState(() {
      informationFilter = newInformationFilter;
      candidates = result;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Candidats'),
      ),
      drawer: const AppDrawer(activeItem: Pages.CANDIDATES),
      bottomSheet: const Copyright(),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              onChanged: (newValue) {
                _filter(newValue);
              },
              decoration: InputDecoration(
                hintText: 'Rechercher...',
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
            child: candidates.isEmpty
                ? const Center(
                    child: Text(
                      "Aucun résultat",
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  )
                : _candidatesList(),
          ),
        ],
      ),
    );
  }

  Widget _candidatesList() {
    return ListView.builder(
      itemCount: candidates.length,
      itemBuilder: (context, index) {
        return _candidateCard(candidate: candidates[index]);
      },
    );
  }

  Widget _candidateCard({required Candidate candidate}) {
    return Card(
      margin: const EdgeInsets.symmetric(
        horizontal: 10.0,
        vertical: 5.0,
      ),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Row(
          children: [
            _buildImage(candidate),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  _buildTitle(candidate),
                  const SizedBox(height: 8),
                  _buildSubtitle(candidate),
                  const SizedBox(height: 8),
                ],
              ),
            ),
            _buildCandidateNumber(candidate),
          ],
        ),
      ),
    );
  }

  Widget _buildImage(Candidate candidate) {
    return Container(
      height: 50,
      width: 50,
      decoration: BoxDecoration(
        borderRadius: const BorderRadius.all(Radius.circular(15.0)),
        image: DecorationImage(
          fit: BoxFit.cover,
          image: FileImage(
            File(
                "${localStorageService.appDocDir.path}/${candidate.imagePath}"),
          ),
        ),
      ),
    );
  }

  Widget _buildTitle(Candidate candidate) {
    return Text(
      candidate.information,
      style: const TextStyle(
        fontWeight: FontWeight.bold,
      ),
    );
  }

  Widget _buildSubtitle(Candidate candidate) {
    return Text(
      '${candidate.politicalEntity} (${candidate.politicalEntityDescription})',
    );
  }

  Widget _buildCandidateNumber(Candidate candidate) {
    return Container(
      padding: const EdgeInsets.all(7),
      child: Center(
        child: Text(
          candidate.candidateNumber.toString(),
          style: const TextStyle(
            color: AppColors.primaryGreen,
            fontSize: 18,
            fontWeight: FontWeight.w900,
          ),
        ),
      ),
    );
  }
}
