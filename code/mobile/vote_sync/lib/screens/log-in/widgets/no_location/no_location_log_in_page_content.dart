import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/dto/election_dto.dart';
import 'package:vote_sync/screens/log-in/widgets/no_location/form/no_location_log_in_page_form.dart';
import 'package:vote_sync/screens/log-in/widgets/with_location/log_in_page.dart';

class NoLocationLogInPageContentWidget extends StatelessWidget {
  final List<ElectionDTO> electionDTOs;
  final Future<void> Function() onRefresh;
  final void Function(String) handlePollingStationSelect;
  final void Function(String) handleElectionSelect;
  final void Function(String) handlePasswordInputChange;
  final void Function() handleFormSubmit;

  const NoLocationLogInPageContentWidget({
    super.key,
    required this.electionDTOs,
    required this.onRefresh,
    required this.handlePollingStationSelect,
    required this.handleElectionSelect,
    required this.handlePasswordInputChange,
    required this.handleFormSubmit,
  });

  @override
  Widget build(BuildContext context) {
    final double screenHeight = MediaQuery.of(context).size.height;
    return RefreshIndicator(
      color: AppColors.primaryGreen,
      backgroundColor: Colors.white,
      onRefresh: () async {
        await onRefresh();
      },
      child: Scaffold(
        backgroundColor: Colors.white,
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            Navigator.of(context).pushReplacement(
              MaterialPageRoute(
                builder: (context) => const LogInPage(),
              ),
            );
          },
          backgroundColor: AppColors.primaryGreen,
          child: const Icon(Icons.location_on),
        ),
        body: SingleChildScrollView(
          physics: const AlwaysScrollableScrollPhysics(),
          child: SizedBox(
            height: screenHeight,
            child: _pageContent(),
          ),
        ),
      ),
    );
  }

  Widget _pageContent() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Expanded(
          child: NoLocationLogInPageForm(
            elections: electionDTOs,
            onPollingStationSelect: handlePollingStationSelect,
            onElectionSelect: handleElectionSelect,
            onPasswordInputChange: handlePasswordInputChange,
            onSubmit: handleFormSubmit,
          ),
        ),
      ],
    );
  }
}
