import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/config/pages.dart';
import 'package:vote_sync/models/voter.dart';
import 'package:vote_sync/widgets/app_drawer.dart';
import 'package:vote_sync/widgets/copyright.dart';

class RegisteredVotersPageContentWidget extends StatelessWidget {
  final List<Voter> voters;
  final int currentPage;
  final int totalPages;
  final String nicFilter;
  final bool hasUnsyncedVoters;
  final Future<void> Function(int page, String nic) filter;
  final void Function(Voter voter) unregister;
  final Widget syncButtonWidget;

  const RegisteredVotersPageContentWidget({
    super.key,
    required this.voters,
    required this.currentPage,
    required this.totalPages,
    required this.nicFilter,
    required this.hasUnsyncedVoters,
    required this.filter,
    required this.unregister,
    required this.syncButtonWidget,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Electeurs enregistrés'),
      ),
      drawer: const AppDrawer(activeItem: Pages.REGISTERED_VOTERS),
      bottomSheet: const Copyright(),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              onChanged: (newValue) {
                filter(1, newValue);
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
          hasUnsyncedVoters ? syncButtonWidget : const SizedBox.shrink(),
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
                    filter(page, nicFilter);
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
                    filter(page, nicFilter);
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
              backgroundColor: AppColors.backgroundColor,
              child: Icon(
                Icons.person,
                color: Colors.black,
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
                      IconButton(
                        icon: const Icon(
                          Icons.delete,
                          color: AppColors.redDanger,
                        ),
                        onPressed: () => {unregister(voter)},
                      ),
                      const Icon(
                        Icons.sync_problem,
                        color: AppColors.redDanger,
                      ),
                    ],
                  ),
          ),
        );
      },
    );
  }
}
