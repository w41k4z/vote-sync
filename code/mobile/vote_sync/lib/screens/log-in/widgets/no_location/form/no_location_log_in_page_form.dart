import 'package:flutter/material.dart';
import 'package:vote_sync/dto/election_dto.dart';
import 'package:vote_sync/screens/log-in/widgets/no_location/form/no_location_log_in_election_selection.dart';
import 'package:vote_sync/screens/log-in/widgets/no_location/form/no_location_log_in_form_logo.dart';
import 'package:vote_sync/screens/log-in/widgets/no_location/form/no_location_log_in_password_input.dart';
import 'package:vote_sync/screens/log-in/widgets/no_location/form/no_location_log_in_submit_button.dart';
import 'package:vote_sync/screens/log-in/widgets/no_location/form/no_location_log_in_polling_station_select.dart';

class NoLocationLogInPageForm extends StatelessWidget {
  final List<ElectionDTO> elections;
  final Function(String) onPollingStationSelect;
  final Function(String) onElectionSelect;
  final Function(String) onPasswordInputChange;
  final Function() onSubmit;

  const NoLocationLogInPageForm({
    super.key,
    required this.elections,
    required this.onPollingStationSelect,
    required this.onElectionSelect,
    required this.onPasswordInputChange,
    required this.onSubmit,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 25.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
          const NoLocationLogInFormLogoWidget(),
          const SizedBox(height: 20),
          // Dropdown for "Bureau de vote"
          NoLocationLogInPollingStationSelectWidget(
            onPollingStationSelect: onPollingStationSelect,
          ),
          const SizedBox(height: 20),
          // Election input
          NoLocationLogInElectionSelectWidget(
            elections: elections,
            onElectionSelect: onElectionSelect,
          ),
          const SizedBox(height: 20),
          // Password input
          NoLocationLogInPasswordInputWidget(
            onPasswordInputChange: onPasswordInputChange,
          ),
          const SizedBox(height: 20),
          NoLocationLogInSubmitButtonWidget(onSubmit: onSubmit),
        ],
      ),
    );
  }
}
