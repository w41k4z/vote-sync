import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/services/api/polling_station_service.dart';

class LogInPage extends StatefulWidget {
  const LogInPage({super.key});

  @override
  State<LogInPage> createState() => _LogInPageState();
}

class _LogInPageState extends State<LogInPage> {
  @override
  void initState() {
    super.initState();
    _getNearestPollingStation();
  }

  Future<void> _getNearestPollingStation() async {
    PollingStationService pollingStationService =
        GetIt.I.get<PollingStationService>();
    await pollingStationService.getNearestPollingStation();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: AppColors.backgroundColor,
        body: Column(
          children: [
            // Header
            Container(
              color: AppColors.primaryGreen,
              height: 150,
              child: const Center(
                child: Text(
                  'VoteSync',
                  style: TextStyle(
                    color: AppColors.secondaryWhite,
                    fontSize: 40,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ),

            Padding(
              padding: const EdgeInsets.symmetric(vertical: 20.0),
              child: Image.asset(
                'assets/ballot_box.png', // Ballot box image path
                height: 100,
              ),
            ),

            // Log in form
            Expanded(
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    // Dropdown for "Bureau de vote"
                    Container(
                      decoration: BoxDecoration(
                        border: Border.all(color: Colors.grey),
                        borderRadius: BorderRadius.circular(10),
                      ),
                      child: DropdownButtonFormField<String>(
                        decoration: const InputDecoration(
                          border: InputBorder.none,
                          contentPadding: EdgeInsets.all(15),
                          prefixIcon: Icon(Icons.location_on),
                          hintText: "Bureau de vote",
                        ),
                        items: <String>['Bureau 1', 'Bureau 2', 'Bureau 3']
                            .map<DropdownMenuItem<String>>((String value) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(value),
                          );
                        }).toList(),
                        onChanged: (String? newValue) {
                          // Handle value change
                        },
                      ),
                    ),

                    const SizedBox(height: 20),

                    // Password input
                    Container(
                      decoration: BoxDecoration(
                        border: Border.all(color: Colors.grey),
                        borderRadius: BorderRadius.circular(10),
                      ),
                      child: TextFormField(
                        obscureText: true,
                        decoration: const InputDecoration(
                          border: InputBorder.none,
                          contentPadding: EdgeInsets.all(15),
                          prefixIcon: Icon(Icons.lock),
                          hintText: "Mot de passe",
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),

            // Bottom section
            Container(
              color: AppColors.primaryGreen,
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(vertical: 20.0),
                    child: SizedBox(
                      width:
                          MediaQuery.of(context).size.width * 0.8, // 80% width
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          backgroundColor:
                              const Color(0xFFDF1515), // Red button
                          padding: const EdgeInsets.symmetric(vertical: 15),
                        ),
                        onPressed: () {
                          // Handle login action
                        },
                        child: const Text(
                          'Se connecter',
                          style: TextStyle(fontSize: 18),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(height: 10),

                  // Copyright text
                  const Text(
                    '© 2024 CENI Madagascar',
                    style: TextStyle(color: Colors.white),
                  ),
                  const SizedBox(height: 20),
                ],
              ),
            ),
          ],
        ));
  }
}
