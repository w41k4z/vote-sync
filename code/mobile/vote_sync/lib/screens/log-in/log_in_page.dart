import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:loader_overlay/loader_overlay.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/dto/polling_station_dto.dart';
import 'package:vote_sync/screens/home/home_page.dart';
import 'package:vote_sync/services/api/auth_service.dart';
import 'package:vote_sync/widgets/copyright.dart';
import 'package:vote_sync/screens/log-in/form/log_in_page_form.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';

class LogInPage extends StatefulWidget {
  const LogInPage({super.key});

  @override
  State<LogInPage> createState() => _LogInPageState();
}

class _LogInPageState extends State<LogInPage> {
  List<PollingStationDTO> pollingStations = [];

  String selectedPollingStationCode = '';
  String password = '';

  @override
  void initState() {
    super.initState();
    _getNearestPollingStation();
  }

  Future<void> _getNearestPollingStation() async {
    context.loaderOverlay.show();
    PollingStationService pollingStationService =
        GetIt.I.get<PollingStationService>();
    List<PollingStationDTO> nearestPollingStations =
        await pollingStationService.getNearestPollingStation();
    setState(() {
      pollingStations = nearestPollingStations;
      context.loaderOverlay.hide();
    });
  }

  void _handlePollingStationsChange(String newValue) {
    setState(() {
      selectedPollingStationCode = newValue;
    });
  }

  void _handlePasswordInputChange(String newValue) {
    setState(() {
      password = newValue;
    });
  }

  void _handleFormSubmit() async {
    context.loaderOverlay.show();
    AuthService authService = GetIt.I.get<AuthService>();
    if (selectedPollingStationCode.isEmpty || password.isEmpty) {
      context.loaderOverlay.hide();
      return;
    }
    try {
      await authService.authenticateUser(selectedPollingStationCode, password);
      // Removes flutter lint warning about context usage after async call
      if (!mounted) return;
      Navigator.of(context).pushReplacement(
        MaterialPageRoute(
          builder: (context) => const HomePage(),
        ),
      );
    } on DioException catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          duration: const Duration(seconds: 5),
          backgroundColor: AppColors.secondaryWhite,
          content: Center(
              child: Text(
            e.response?.data["message"],
            style: const TextStyle(color: AppColors.redDanger),
          )),
        ),
      );
    } finally {
      context.loaderOverlay.hide();
    }
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
            onSubmit: _handleFormSubmit,
          ),
        ),
        const Copyright()
      ],
    );
  }
}
