import 'package:flutter/material.dart';
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
        border: Border.all(color: Colors.black),
        borderRadius: BorderRadius.circular(10),
      ),
      child: DropdownButtonFormField<String>(
        decoration: const InputDecoration(
          border: InputBorder.none,
          contentPadding: EdgeInsets.all(15),
          prefixIcon: Icon(Icons.calendar_today),
          prefixIconColor: Colors.black,
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
          style: TextStyle(color: Colors.black, fontSize: 16),
        ),
        dropdownColor: theme.colorScheme.onPrimary,
        style: const TextStyle(color: Colors.black),
        iconEnabledColor: Colors.black,
      ),
    );
  }
}
