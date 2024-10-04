import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/dto/polling_station.dart';

class LogInPollingStationSelectWidget extends StatelessWidget {
  final Function(String) onPollingStationSelect;

  const LogInPollingStationSelectWidget(
      {super.key,
      required this.pollingStations,
      required this.onPollingStationSelect});

  final List<PollingStation> pollingStations;

  @override
  Widget build(BuildContext context) {
    return Container(
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
        items: pollingStations
            .map<DropdownMenuItem<String>>((PollingStation value) {
          return DropdownMenuItem<String>(
            value: value.id.toString(),
            child: Text(value.name),
          );
        }).toList(),
        style: const TextStyle(color: Colors.white),
        onChanged: (String? newValue) {
          if (newValue != null) {
            onPollingStationSelect(newValue);
          }
        },
      ),
    );
  }
}
