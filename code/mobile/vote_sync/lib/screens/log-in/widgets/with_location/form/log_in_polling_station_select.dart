import 'package:flutter/material.dart';
import 'package:vote_sync/dto/polling_station_dto.dart';

class LogInPollingStationSelectWidget extends StatelessWidget {
  final List<PollingStationDTO> pollingStations;
  final Function(String) onPollingStationSelect;

  const LogInPollingStationSelectWidget({
    super.key,
    required this.pollingStations,
    required this.onPollingStationSelect,
  });

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    return Container(
      decoration: BoxDecoration(
        border: Border.all(color: Colors.black),
        borderRadius: BorderRadius.circular(10),
      ),
      child: DropdownButtonFormField<String>(
        decoration: const InputDecoration(
          border: InputBorder.none,
          contentPadding: EdgeInsets.all(15),
          prefixIcon: Icon(Icons.location_on),
          prefixIconColor: Colors.black,
        ),
        items: pollingStations.isEmpty
            ? const <DropdownMenuItem<String>>[
                DropdownMenuItem<String>(
                  value: null,
                  child: Text("Aucun bureau de vote à proximité"),
                )
              ]
            : pollingStations
                .map<DropdownMenuItem<String>>((PollingStationDTO value) {
                return DropdownMenuItem<String>(
                  value: value.code,
                  child: Text(value.name),
                );
              }).toList(),
        onChanged: (String? newValue) {
          if (newValue != null) {
            onPollingStationSelect(newValue);
          }
        },
        hint: const Text(
          "Bureau de vote",
          style: TextStyle(
            color: Colors.black,
            fontSize: 16,
          ),
        ),
        dropdownColor: theme.colorScheme.onPrimary,
        style: const TextStyle(
          color: Colors.black,
        ),
        iconEnabledColor: Colors.black,
      ),
    );
  }
}
