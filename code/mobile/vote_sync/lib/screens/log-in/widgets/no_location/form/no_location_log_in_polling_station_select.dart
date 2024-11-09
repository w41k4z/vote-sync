import 'package:flutter/material.dart';

class NoLocationLogInPollingStationSelectWidget extends StatelessWidget {
  final Function(String) onPollingStationSelect;

  const NoLocationLogInPollingStationSelectWidget({
    super.key,
    required this.onPollingStationSelect,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        border: Border.all(color: Colors.black),
        borderRadius: BorderRadius.circular(10),
      ),
      child: TextFormField(
        decoration: const InputDecoration(
          border: InputBorder.none,
          contentPadding: EdgeInsets.all(15),
          prefixIcon: Icon(Icons.lock),
          prefixIconColor: Colors.black,
          hintText: "Code bureau de vote",
        ),
        style: const TextStyle(color: Colors.black),
        onChanged: (value) {
          onPollingStationSelect(value);
        },
      ),
    );
  }
}
