import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class LogInPasswordInputWidget extends StatelessWidget {
  final Function(String) onPasswordInputChange;

  const LogInPasswordInputWidget(
      {super.key, required this.onPasswordInputChange});

  @override
  Widget build(BuildContext context) {
    return Container(
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
        ),
        style: const TextStyle(color: Colors.white),
        onChanged: (value) {
          onPasswordInputChange(value);
        },
      ),
    );
  }
}
