import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:vote_sync/config/app_colors.dart';

class LoadingSpinner extends StatelessWidget {
  const LoadingSpinner({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: SpinKitWaveSpinner(
        size: 75.0,
        color: Colors.white,
        waveColor: AppColors.primaryGreen,
      ),
    );
  }
}
