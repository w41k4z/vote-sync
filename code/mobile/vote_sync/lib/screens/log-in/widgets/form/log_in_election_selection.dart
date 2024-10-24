import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';
import 'package:vote_sync/dto/election_dto.dart';

class LogInElectionSelectWidget extends StatelessWidget {
  final List<ElectionDTO> elections;
  final Function(String) onElectionSelect;

  const LogInElectionSelectWidget({
    super.key,
    required this.elections,
    required this.onElectionSelect,
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
          prefixIcon: Icon(Icons.calendar_today),
          prefixIconColor: Colors.white,
        ),
        items: elections.isEmpty
            ? const <DropdownMenuItem<String>>[
                DropdownMenuItem<String>(
                  value: null,
                  child: Text("Aucune élection en cours"),
                )
              ]
            : elections.map<DropdownMenuItem<String>>((ElectionDTO value) {
                return DropdownMenuItem<String>(
                  value: value.id.toString(),
                  child: Text(value.name),
                );
              }).toList(),
        onChanged: (String? newValue) {
          if (newValue != null) {
            onElectionSelect(newValue);
          }
        },
        hint: const Text(
          "Election en cours",
          style: TextStyle(color: Colors.white, fontSize: 16),
        ),
        dropdownColor: theme.colorScheme.onSurface.withOpacity(0.9),
        style: const TextStyle(color: Colors.white),
        iconEnabledColor: Colors.white,
      ),
    );
  }
}
