import 'dart:math';

import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class ElectionStatResult {
  static List<Widget> widgets() {
    return [
      _registeredVoters(),
      _excludedVoteInformation(),
      _registeredVoteInformation(),
    ];
  }

  static Widget _registeredVoters() {
    return Card(
      margin: const EdgeInsets.symmetric(
        horizontal: 10.0,
        vertical: 5.0,
      ),
      child: ListTile(
        leading: const CircleAvatar(
          backgroundColor: AppColors.backgroundColor,
          child: Icon(
            Icons.how_to_vote_outlined,
            color: Colors.black,
          ),
        ),
        title: Row(
          children: [
            Text(
              Random().nextInt(100).toString(),
              style: const TextStyle(
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(width: 5),
            const Text(
              'Enregistrés',
            ),
          ],
        ),
        trailing: const Text('0%'),
      ),
    );
  }

  static Widget _excludedVoteInformation() {
    return Card(
      margin: const EdgeInsets.symmetric(
        horizontal: 10.0,
        vertical: 5.0,
      ),
      child: Row(
        children: [
          Expanded(
            child: ListTile(
              leading: const CircleAvatar(
                backgroundColor: AppColors.backgroundColor,
                child: Icon(
                  Icons.close,
                  color: Colors.black,
                ),
              ),
              title: Row(
                children: [
                  Text(
                    Random().nextInt(100).toString(),
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(width: 5),
                  const Text(
                    'Nuls',
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: ListTile(
              leading: const CircleAvatar(
                backgroundColor: AppColors.backgroundColor,
                child: Icon(
                  Icons.layers_clear_outlined,
                  color: Colors.black,
                ),
              ),
              title: Row(
                children: [
                  Text(
                    Random().nextInt(100).toString(),
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(width: 5),
                  const Text(
                    'Blancs',
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  static Widget _registeredVoteInformation() {
    return Card(
      margin: const EdgeInsets.symmetric(
        horizontal: 10.0,
        vertical: 5.0,
      ),
      child: ListTile(
        leading: const CircleAvatar(
          backgroundColor: AppColors.backgroundColor,
          child: Icon(
            Icons.checklist,
            color: Colors.black,
          ),
        ),
        title: Row(
          children: [
            Text(
              Random().nextInt(100).toString(),
              style: const TextStyle(
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(width: 5),
            const Text(
              'Exprimés',
            ),
          ],
        ),
        trailing: const Text('0%'),
      ),
    );
  }
}
