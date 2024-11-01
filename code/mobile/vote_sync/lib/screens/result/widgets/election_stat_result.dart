import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/models/polling_station.dart';

class ElectionStatResult {
  static List<Widget> widgets({
    required PollingStation? pollingStation,
    required int registered,
    required int voters,
  }) {
    return [
      _registeredVoters(registered, voters),
      _excludedVoteInformation(pollingStation),
      _registeredVoteInformation(pollingStation, registered),
    ];
  }

  static Widget _registeredVoters(int registered, int voters) {
    double rate = 0;
    if (voters > 0) {
      rate = ((registered * 100) / voters).roundToDouble();
    }
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
              registered.toString(),
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
        trailing: Text('$rate%'),
      ),
    );
  }

  static Widget _excludedVoteInformation(PollingStation? pollingStation) {
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
                    pollingStation != null
                        ? pollingStation.nulls.toString()
                        : '0',
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                      color: AppColors.redDanger,
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
                    pollingStation != null
                        ? pollingStation.blanks.toString()
                        : '0',
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                      color: AppColors.redDanger,
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

  static Widget _registeredVoteInformation(
    PollingStation? pollingStation,
    int registered,
  ) {
    int validVotes = registered;
    if (pollingStation != null) {
      int invalidVotes = pollingStation.blanks + pollingStation.nulls;
      validVotes -= invalidVotes;
    }
    double rate = 0;
    if (validVotes > 0) {
      rate = ((validVotes * 100) / registered).roundToDouble();
    }
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
              validVotes.toString(),
              style: const TextStyle(
                fontWeight: FontWeight.bold,
                color: AppColors.primaryGreen,
              ),
            ),
            const SizedBox(width: 5),
            const Text(
              'Exprimés',
            ),
          ],
        ),
        trailing: Text('$rate%'),
      ),
    );
  }
}
