import 'package:flutter/material.dart';
import 'package:vote_sync/config/app_colors.dart';

class NoLocationLogInSubmitButtonWidget extends StatelessWidget {
  final Function() onSubmit;

  const NoLocationLogInSubmitButtonWidget({
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
            backgroundColor: AppColors.primaryGreen, // Red button
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
