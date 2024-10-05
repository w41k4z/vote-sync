import 'package:flutter/material.dart';

class LogInSubmitButtonWidget extends StatelessWidget {
  final Function() onSubmit;

  const LogInSubmitButtonWidget({
    super.key,
    required this.onSubmit,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 20.0),
      child: SizedBox(
        width: MediaQuery.of(context).size.width, // 100% width
        child: ElevatedButton(
          style: ElevatedButton.styleFrom(
            backgroundColor: const Color(0xFFDF1515), // Red button
            padding: const EdgeInsets.symmetric(vertical: 15),
          ),
          onPressed: () {
            onSubmit();
          },
          child: const Text(
            'Se connecter',
            style: TextStyle(fontSize: 18, color: Colors.white),
          ),
        ),
      ),
    );
  }
}
