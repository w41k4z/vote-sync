import 'dart:io';

import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:sqflite/sqflite.dart';
import 'package:vote_sync/config/page_content.dart';
import 'package:vote_sync/models/candidate.dart';
import 'package:vote_sync/services/data/database_manager.dart';
import 'package:vote_sync/services/data/domain/candidate_domain_service.dart';
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

  @override
  void initState() {
    super.initState();
    _getCandidates();
  }

  Future<void> _getCandidates() async {
    Database databaseInstance = GetIt.I.get<DatabaseManager>().database;
    List<Candidate> result =
        await GetIt.I.get<CandidateDomainService>().findAll(databaseInstance);
    setState(() {
      candidates = result;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Candidats'),
      ),
      drawer: const AppDrawer(activeItem: PageContent.CANDIDATES),
      bottomSheet: const Copyright(),
      body: Column(
        children: [
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
      elevation: 4,
      margin: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          _buildImage(candidate),
          const SizedBox(height: 16),
          _buildCandidateInfo(candidate),
        ],
      ),
    );
  }

  Widget _buildImage(Candidate candidate) {
    return Container(
      height: 200,
      width: double.infinity,
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(8),
        image: DecorationImage(
          fit: BoxFit.cover,
          image: FileImage(File(
              "${localStorageService.appDocDir.path}/${candidate.imagePath}")),
        ),
      ),
    );
  }

  Widget _buildCandidateInfo(Candidate candidate) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          'Registration ID: ${candidate.registrationId}',
          style: const TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
        const SizedBox(height: 4),
        Text('Registration Date: ${candidate.registrationDate}'),
        const SizedBox(height: 4),
        Text('Candidate Number: ${candidate.candidateNumber}'),
        const SizedBox(height: 4),
        Text('Information: ${candidate.information}'),
        const SizedBox(height: 4),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            const Text(
              'Entité politique:',
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
            Text(
              '${candidate.politicalEntity} (${candidate.politicalEntityDescription})',
            ),
          ],
        ),
      ],
    );
  }
}
