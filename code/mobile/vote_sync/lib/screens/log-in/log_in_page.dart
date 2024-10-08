import 'dart:developer';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:loader_overlay/loader_overlay.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/dto/election_dto.dart';
import 'package:vote_sync/dto/polling_station_dto.dart';
import 'package:vote_sync/screens/home/home_page.dart';
import 'package:vote_sync/services/api/auth_service.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/data/database_manager.dart';
import 'package:vote_sync/widgets/error/global_error_handler.dart';
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
  List<ElectionDTO> elections = [];

  String selectedPollingStationCode = '';
  String selectedElectionId = '';
  String password = '';

  @override
  void initState() {
    super.initState();
    _getNearestPollingStationAndCurrentElections();
  }

  @override
  Widget build(BuildContext context) {
    final double screenHeight = MediaQuery.of(context).size.height;
    return RefreshIndicator(
      color: AppColors.primaryGreen,
      backgroundColor: Colors.white,
      onRefresh: () async {
        await _getNearestPollingStationAndCurrentElections();
      },
      child: Scaffold(
        backgroundColor: AppColors.primaryGreen,
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
          child: LogInPageForm(
            pollingStations: pollingStations,
            elections: elections,
            onPollingStationSelect: _handlePollingStationsChange,
            onElectionSelect: _handleElectionSelect,
            onPasswordInputChange: _handlePasswordInputChange,
            onSubmit: _handleFormSubmit,
          ),
        ),
        const Copyright()
      ],
    );
  }

  Future<void> _getNearestPollingStationAndCurrentElections() async {
    context.loaderOverlay.show();
    PollingStationService pollingStationService =
        GetIt.I.get<PollingStationService>();
    try {
      List<dynamic> payload = await pollingStationService
          .getNearestPollingStationAndCurrentElections();
      setState(() {
        pollingStations = payload[0];
        elections = payload[1];
        context.loaderOverlay.hide();
      });
    } on DioException catch (e) {
      log(e.toString());
      if (e.type == DioExceptionType.connectionError) {
        _showInternetAccessErrorDialog();
      } else {
        _showSnackBarError(e.toString());
      }
    } catch (e) {
      log(e.toString());
      _showSnackBarError(e.toString());
    } finally {
      if (mounted) context.loaderOverlay.hide();
    }
  }

  void _handlePollingStationsChange(String newValue) {
    setState(() {
      selectedPollingStationCode = newValue;
    });
  }

  void _handleElectionSelect(String newValue) {
    setState(() {
      selectedElectionId = newValue;
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
    AppInstance appInstance = GetIt.I.get<AppInstance>();
    if (selectedPollingStationCode.isEmpty ||
        password.isEmpty ||
        selectedElectionId.isEmpty) {
      context.loaderOverlay.hide();
      _showSnackBarError("Veuillez remplir tous les champs");
      return;
    }
    try {
      // Retrieving the access token
      String accessToken = await authService.authenticateUser(
          selectedPollingStationCode, password);
      PollingStationDTO pollingStationDTO = selectedPollingStationDTO;
      ElectionDTO electionDTO = selectedElectionDTO;

      // Granting access by setting the access token and polling station id
      // to the app instance
      await appInstance.grantAccess(
          accessToken, pollingStationDTO.id.toString(), selectedElectionId);

      DatabaseManager databaseManager = GetIt.I.get<DatabaseManager>();
      bool isDatabasePopulated =
          await databaseManager.isDatabasePopulated(pollingStationDTO.id);

      if (!isDatabasePopulated) {
        // Fetching the polling station data
        PollingStationService pollingStationService =
            GetIt.I.get<PollingStationService>();
        final data = await pollingStationService.getPollingStationData(
            pollingStationDTO.id, int.parse(electionDTO.id.toString()));
        await databaseManager.populateDatabase(data["pollingStation"],
            data["election"], data["voters"], data["candidates"]);
      }

      // Removes flutter lint warning about context usage with async call
      // (Care to always make sure to await the async call before using context)
      if (!mounted) return;
      Navigator.of(context).pushReplacement(
        MaterialPageRoute(
          builder: (context) => const HomePage(),
        ),
      );
    } on DioException catch (e) {
      appInstance.logout();
      if (!mounted) return;
      _showSnackBarError(e.response?.data["message"]);
    } finally {
      context.loaderOverlay.hide();
    }
  }

  PollingStationDTO get selectedPollingStationDTO {
    return pollingStations.firstWhere(
      (element) => element.code == selectedPollingStationCode,
    );
  }

  ElectionDTO get selectedElectionDTO {
    return elections.firstWhere(
      (element) => element.id.toString() == selectedElectionId,
    );
  }

  void _showInternetAccessErrorDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return GlobalErrorHandler.internetAccessErrorDialog(
          context: context,
          onRetry: () {
            _getNearestPollingStationAndCurrentElections();
          },
        );
      },
    );
  }

  void _showSnackBarError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        duration: const Duration(seconds: 5),
        backgroundColor: AppColors.secondaryWhite,
        content: Center(
          child: Text(
            message,
            style: const TextStyle(color: AppColors.redDanger),
          ),
        ),
      ),
    );
  }
}
