import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:vote_sync/config/app_colors.dart';

class ResultBottomNavigationBar extends StatelessWidget {
  final Function() onScanPressed;
  final Function() onUploadPressed;
  final Function() onEditPressed;

  const ResultBottomNavigationBar({
    super.key,
    required this.onScanPressed,
    required this.onUploadPressed,
    required this.onEditPressed,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 60,
      padding: const EdgeInsets.symmetric(horizontal: 20),
      decoration: BoxDecoration(
        color: Colors.white,
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.3),
            blurRadius: 10,
            offset: const Offset(0, -3),
          ),
        ],
        borderRadius: const BorderRadius.vertical(top: Radius.circular(20)),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          // Scan Icon (Left)
          IconButton(
            icon: Icon(
              FontAwesomeIcons.fileLines,
              size: 25,
              color: Colors.grey[400],
            ),
            onPressed: onScanPressed,
          ),
          // Upload Icon (Center - Highlighted)
          GestureDetector(
            onTap: onUploadPressed,
            child: Container(
              height: 50,
              width: 50,
              decoration: BoxDecoration(
                color: AppColors.primaryGreen,
                shape: BoxShape.circle,
                boxShadow: [
                  BoxShadow(
                    color: AppColors.primaryGreen.withOpacity(0.6),
                    blurRadius: 8,
                    offset: const Offset(0, 4),
                  ),
                ],
              ),
              child: const Icon(
                Icons.cloud_upload,
                size: 35,
                color: Colors.white,
              ),
            ),
          ),
          // Edit Icon (Right)
          IconButton(
            icon: Icon(
              FontAwesomeIcons.penToSquare,
              size: 25,
              color: Colors.grey[400],
            ),
            onPressed: onEditPressed,
          ),
        ],
      ),
    );
  }
}
