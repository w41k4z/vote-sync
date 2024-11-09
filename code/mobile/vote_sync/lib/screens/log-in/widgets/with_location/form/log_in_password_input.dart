import 'package:flutter/material.dart';

class LogInPasswordInputWidget extends StatelessWidget {
  final Function(String) onPasswordInputChange;

  const LogInPasswordInputWidget(
      {super.key, required this.onPasswordInputChange});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        border: Border.all(color: Colors.black),
        borderRadius: BorderRadius.circular(10),
      ),
      child: TextFormField(
        obscureText: true,
        decoration: const InputDecoration(
          border: InputBorder.none,
          contentPadding: EdgeInsets.all(15),
          prefixIcon: Icon(Icons.lock),
          prefixIconColor: Colors.black,
          hintText: "Mot de passe",
          hintStyle: TextStyle(color: Colors.black),
        ),
        style: const TextStyle(color: Colors.black),
        onChanged: (value) {
          onPasswordInputChange(value);
        },
      ),
    );
  }
}
