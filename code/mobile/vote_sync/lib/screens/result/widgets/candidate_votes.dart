import 'dart:io';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/models/candidate.dart';
import 'package:vote_sync/services/local_storage_service.dart';

class CandidateVotes {
  static List<Widget> widgets({
    required List<Candidate> candidates,
    required LocalStorageService localStorageService,
  }) {
    print(candidates);
    return _candidatesList(candidates, localStorageService);
  }

  static List<Widget> _candidatesList(
    List<Candidate> candidates,
    LocalStorageService localStorageService,
  ) {
    return List.generate(candidates.length, (index) {
      return _candidateCard(
        candidate: candidates[index],
        votes: Random().nextInt(700),
        localStorageService: localStorageService,
      );
    });
  }

  static Widget _candidateCard({
    required Candidate candidate,
    required int votes,
    required LocalStorageService localStorageService,
  }) {
    return Card(
      margin: const EdgeInsets.symmetric(
        horizontal: 10.0,
        vertical: 5.0,
      ),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Row(
          children: [
            _buildImage(candidate, localStorageService),
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
            _buildCandidateVotes(candidate, votes),
          ],
        ),
      ),
    );
  }

  static Widget _buildImage(
    Candidate candidate,
    LocalStorageService localStorageService,
  ) {
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

  static Widget _buildTitle(Candidate candidate) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          'Numéro: ${candidate.candidateNumber}',
          style: const TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 16,
          ),
        ),
        const SizedBox(
          height: 5,
        ),
        Text(
          candidate.information,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ],
    );
  }

  static Widget _buildSubtitle(Candidate candidate) {
    return Text(
      '${candidate.politicalEntity} (${candidate.politicalEntityDescription})',
    );
  }

  static Widget _buildCandidateVotes(Candidate candidate, int votes) {
    return Center(
      child: Text(
        votes.toString(),
        style: const TextStyle(
          color: AppColors.primaryGreen,
          fontSize: 18,
          fontWeight: FontWeight.w900,
        ),
      ),
    );
  }
}
