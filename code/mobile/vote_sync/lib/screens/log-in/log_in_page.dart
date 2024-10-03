import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/dto/polling_station.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';

class LogInPage extends StatefulWidget {
  const LogInPage({super.key});

  @override
  State<LogInPage> createState() => _LogInPageState();
}

class _LogInPageState extends State<LogInPage> {
  List<PollingStation> pollingStations = [];
  bool isLoading = false;

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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: AppColors.primaryGreen, body: _mainContainer());
  }

  Widget _mainContainer() {
    return SafeArea(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [Expanded(child: _logInForm()), _contentFooter()],
      ),
    );
  }

  Widget _contentHeader() {
    return Container(
      margin: const EdgeInsets.only(top: 15),
      child: Center(
        child: Image.asset("assets/images/logo.png"),
      ),
    );
  }

  Widget _logInForm() {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 25.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
          Container(
            margin: const EdgeInsets.only(top: 15),
            child: Center(
              child: Image.asset("assets/images/logo.png"),
            ),
          ),

          const SizedBox(height: 20),

          // Dropdown for "Bureau de vote"
          Container(
            decoration: BoxDecoration(
              border: Border.all(color: AppColors.secondaryWhite),
              borderRadius: BorderRadius.circular(10),
            ),
            child: DropdownButtonFormField<String>(
              decoration: const InputDecoration(
                  border: InputBorder.none,
                  contentPadding: EdgeInsets.all(15),
                  prefixIcon: Icon(Icons.location_on),
                  prefixIconColor: Colors.white,
                  focusColor: Colors.white,
                  hintText: "Bureau de vote",
                  hintStyle: TextStyle(color: Colors.white)),
              items: pollingStations.isEmpty && !isLoading
                  ? const <DropdownMenuItem<String>>[]
                  : pollingStations
                      .map<DropdownMenuItem<String>>((PollingStation value) {
                      return DropdownMenuItem<String>(
                        value: value.id.toString(),
                        child: Text(value.name),
                      );
                    }).toList(),
              style: const TextStyle(color: Colors.white),
              onChanged: (String? newValue) {
                print("Selected: $newValue");
              },
            ),
          ),

          const SizedBox(height: 20),

          // Password input
          Container(
            decoration: BoxDecoration(
              border: Border.all(color: AppColors.secondaryWhite),
              borderRadius: BorderRadius.circular(10),
            ),
            child: TextFormField(
              obscureText: true,
              decoration: const InputDecoration(
                  border: InputBorder.none,
                  contentPadding: EdgeInsets.all(15),
                  prefixIcon: Icon(Icons.lock),
                  prefixIconColor: Colors.white,
                  hintText: "Mot de passe",
                  hintStyle: TextStyle(color: Colors.white),
                  focusColor: Colors.white,
                  hoverColor: Colors.white),
              style: const TextStyle(color: Colors.white),
            ),
          ),

          const SizedBox(height: 20),

          Container(
            padding: const EdgeInsets.symmetric(vertical: 20.0),
            child: SizedBox(
              width: MediaQuery.of(context).size.width, // 100% width
              child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                  backgroundColor: const Color(0xFFDF1515), // Red button
                  padding: const EdgeInsets.symmetric(vertical: 15),
                ),
                onPressed: () {
                  // Handle login action
                },
                child: const Text(
                  'Se connecter',
                  style: TextStyle(fontSize: 18, color: Colors.white),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _contentFooter() {
    return Container(
      color: AppColors.primaryGreen,
      child: const Column(
        children: [
          // Copyright text
          Text(
            '© 2024 CENI Madagascar',
            style: TextStyle(color: Colors.white),
          ),
          SizedBox(height: 20),
        ],
      ),
    );
  }
}
