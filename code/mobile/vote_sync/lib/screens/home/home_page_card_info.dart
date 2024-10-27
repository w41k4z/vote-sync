import 'package:flutter/material.dart';

class HomePageCardInfo extends StatelessWidget {
  final Color color;
  final IconData iconData;
  final Color iconColor;
  final String title;
  final Color titleColor;
  final List<String> content;
  final Color contenTextColor;

  const HomePageCardInfo({
    super.key,
    this.color = Colors.white,
    required this.iconData,
    this.iconColor = Colors.black,
    required this.title,
    this.titleColor = Colors.black,
    required this.content,
    this.contenTextColor = Colors.black,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      color: color,
      elevation: 2,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Row(
          children: [
            Icon(
              iconData,
              size: 35,
              color: iconColor,
            ),
            const SizedBox(width: 20),
            Flexible(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    title,
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      color: titleColor,
                    ),
                  ),
                  for (String each in content)
                    Text(
                      each,
                      style: TextStyle(
                        fontSize: 15,
                        color: contenTextColor,
                      ),
                    ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
