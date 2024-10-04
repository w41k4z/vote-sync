import 'package:flutter/material.dart';
import 'package:vote_sync/dto/polling_station.dart';
import 'package:vote_sync/screens/log-in/form/log_in_form_logo.dart';
import 'package:vote_sync/screens/log-in/form/log_in_password_input.dart';
import 'package:vote_sync/screens/log-in/form/log_in_submit_button.dart';
import 'package:vote_sync/screens/log-in/form/log_in_polling_station_select.dart';

class LogInPageForm extends StatelessWidget {
  final List<PollingStation> pollingStations;
  final Function(String) onPollingStationSelect;
  final Function(String) onPasswordInputChange;

  const LogInPageForm({
    super.key,
    required this.pollingStations,
    required this.onPollingStationSelect,
    required this.onPasswordInputChange,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 25.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
          const LogInFormLogoWidget(),
          const SizedBox(height: 20),
          // Dropdown for "Bureau de vote"
          LogInPollingStationSelectWidget(
            pollingStations: pollingStations,
            onPollingStationSelect: onPollingStationSelect,
          ),
          const SizedBox(height: 20),
          // Password input
          LogInPasswordInputWidget(
            onPasswordInputChange: onPasswordInputChange,
          ),
          const SizedBox(height: 20),
          const LogInSubmitButtonWidget(),
        ],
      ),
    );
  }
}
