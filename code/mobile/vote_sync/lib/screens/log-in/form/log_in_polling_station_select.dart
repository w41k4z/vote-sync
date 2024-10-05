import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';
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
        border: Border.all(color: AppColors.secondaryWhite),
        borderRadius: BorderRadius.circular(10),
      ),
      child: DropdownButtonFormField<String>(
        decoration: const InputDecoration(
          border: InputBorder.none,
          contentPadding: EdgeInsets.all(15),
          prefixIcon: Icon(Icons.location_on),
          prefixIconColor: Colors.white,
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
          style: TextStyle(color: Colors.white, fontSize: 16),
        ),
        dropdownColor: theme.colorScheme.onSurface.withOpacity(0.9),
        style: const TextStyle(color: Colors.white),
        iconEnabledColor: Colors.white,
      ),
    );
  }
}
