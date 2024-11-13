import 'dart:developer';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:loader_overlay/loader_overlay.dart';
import 'package:vote_sync/dto/election_dto.dart';
import 'package:vote_sync/dto/polling_station_dto.dart';
import 'package:vote_sync/screens/home/home_page.dart';
import 'package:vote_sync/screens/log-in/widgets/no_location/no_location_log_in_page_content.dart';
import 'package:vote_sync/services/api/auth_service.dart';
import 'package:vote_sync/services/api/election_service.dart';
import 'package:vote_sync/services/app_instance.dart';
import 'package:vote_sync/services/database_manager.dart';
import 'package:vote_sync/widgets/error/global_error_handler.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';
import 'package:vote_sync/widgets/error/snack_bar_error.dart';

class NoLocationLogInPage extends StatefulWidget {
  const NoLocationLogInPage({super.key});

  @override
  State<NoLocationLogInPage> createState() => _NoLocationLogInPageState();
}

class _NoLocationLogInPageState extends State<NoLocationLogInPage> {
  List<ElectionDTO> elections = [];

  String selectedPollingStationCode = '';
  String selectedElectionId = '';
  String password = '';

  ElectionDTO get selectedElectionDTO {
    return elections.firstWhere(
      (element) => element.id.toString() == selectedElectionId,
    );
  }

  @override
  void initState() {
    super.initState();
    _getCurrentElections();
  }

  @override
  Widget build(BuildContext context) {
    return NoLocationLogInPageContentWidget(
      electionDTOs: elections,
      onRefresh: _getCurrentElections,
      handlePollingStationSelect: _handlePollingStationSelect,
      handleElectionSelect: _handleElectionSelect,
      handlePasswordInputChange: _handlePasswordInputChange,
      handleFormSubmit: _handleFormSubmit,
    );
  }

  void _handlePollingStationSelect(String newValue) {
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
    await _submitForm();
  }

  Future<void> _getCurrentElections() async {
    context.loaderOverlay.show();
    ElectionService electionService = GetIt.I.get<ElectionService>();
    try {
      List<ElectionDTO> currentElections =
          await electionService.getCurrentElections();
      setState(() {
        elections = currentElections;
        context.loaderOverlay.hide();
      });
    } on DioException catch (e) {
      log(e.toString());
      // Removes flutter lint warning about context usage with async call
      // (Care to always make sure to await the async call before using context)
      if (!mounted) return;
      if (e.type == DioExceptionType.connectionError) {
        GlobalErrorHandler.internetAccessErrorDialog(
          context: context,
          onRetry: _getCurrentElections,
        );
      } else {
        SnackBarError.show(context: context, message: e.toString());
      }
    } catch (e) {
      log(e.toString());
      if (!mounted) return;
      SnackBarError.show(context: context, message: e.toString());
    } finally {
      if (mounted) context.loaderOverlay.hide();
    }
  }

  Future<void> _submitForm() async {
    context.loaderOverlay.show();
    AuthService authService = GetIt.I.get<AuthService>();
    AppInstance appInstance = GetIt.I.get<AppInstance>();
    if (selectedPollingStationCode.isEmpty ||
        password.isEmpty ||
        selectedElectionId.isEmpty) {
      context.loaderOverlay.hide();
      SnackBarError.show(
        context: context,
        message: 'Veuillez remplir tous les champs',
      );
      return;
    }
    try {
      // Retrieving the access token
      String accessToken = await authService.authenticateUser(
          selectedPollingStationCode, password);
      ElectionDTO electionDTO = selectedElectionDTO;
      PollingStationDTO pollingStationDTO =
          await GetIt.I.get<PollingStationService>().getPollingStationByCode(
                selectedPollingStationCode,
                electionDTO.id,
                accessToken,
              );

      // Granting access by setting the access token and polling station id
      // to the app instance
      await appInstance.grantAccess(
        accessToken,
        pollingStationDTO.id.toString(),
        selectedElectionId,
      );

      DatabaseManager databaseManager = GetIt.I.get<DatabaseManager>();
      bool isDatabasePopulated = await databaseManager.isDatabasePopulated(
          pollingStationDTO.id.toString(), electionDTO.id.toString());
      if (!isDatabasePopulated) {
        // Fetching the polling station data
        PollingStationService pollingStationService =
            GetIt.I.get<PollingStationService>();
        final data = await pollingStationService.getPollingStationData(
          pollingStationDTO.id,
          electionDTO.id,
        );
        // Populating the app database
        await databaseManager.populateDatabase(data["pollingStation"],
            data["election"], data["voters"], data["candidates"]);
      }
      if (!mounted) return;
      Navigator.of(context).pushReplacement(
        MaterialPageRoute(
          builder: (context) => const HomePage(),
        ),
      );
    } on DioException catch (e) {
      log(e.toString());
      await appInstance.logout();
      if (!mounted) return;
      if (e.type == DioExceptionType.connectionError) {
        GlobalErrorHandler.internetAccessErrorDialog(
          context: context,
          onRetry: _handleFormSubmit,
        );
      } else {
        SnackBarError.show(
          context: context,
          message: e.response?.data["message"],
        );
      }
    } catch (e) {
      log(e.toString());
      await appInstance.logout();
      if (!mounted) return;
      SnackBarError.show(
        context: context,
        message: e.toString(),
      );
    } finally {
      context.loaderOverlay.hide();
    }
  }
}
