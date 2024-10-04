import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/dto/polling_station.dart';
import 'package:vote_sync/screens/log-in/log_in_page_footer.dart';
import 'package:vote_sync/screens/log-in/log_in_page_form.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';

class LogInPage extends StatefulWidget {
  const LogInPage({super.key});

  @override
  State<LogInPage> createState() => _LogInPageState();
}

class _LogInPageState extends State<LogInPage> {
  List<PollingStation> pollingStations = [];
  bool isLoading = false;

  String selectedPollingStationId = '';
  String password = '';

  @override
  void initState() {
    super.initState();
    _getNearestPollingStation();
  }

  Future<void> _getNearestPollingStation() async {
    setState(() {
      isLoading = true;
    });
    PollingStationService pollingStationService =
        GetIt.I.get<PollingStationService>();
    List<PollingStation> nearestPollingStations =
        await pollingStationService.getNearestPollingStation();
    setState(() {
      isLoading = true;
      pollingStations = nearestPollingStations;
    });
  }

  void _handlePollingStationsChange(String newValue) {
    print("Selected: $newValue");
    setState(() {
      selectedPollingStationId = newValue;
    });
  }

  void _handlePasswordInputChange(String newValue) {
    print("Password: $newValue");
    setState(() {
      password = newValue;
    });
  }

  @override
  Widget build(BuildContext context) {
    final double screenHeight = MediaQuery.of(context).size.height;
    final double keyboardHeight = MediaQuery.of(context).viewInsets.bottom;
    return Scaffold(
      backgroundColor: AppColors.primaryGreen,
      body: SingleChildScrollView(
        child: SizedBox(
          height: screenHeight - keyboardHeight,
          child: _mainContainer(),
        ),
      ),
    );
  }

  Widget _mainContainer() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Expanded(
          child: LogInPageForm(
            pollingStations: pollingStations,
            onPollingStationSelect: _handlePollingStationsChange,
            onPasswordInputChange: _handlePasswordInputChange,
          ),
        ),
        const LogInPageFooter()
      ],
    );
  }
}
