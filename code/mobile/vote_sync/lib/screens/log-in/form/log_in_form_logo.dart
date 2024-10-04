import 'package:flutter/widgets.dart';

class LogInFormLogoWidget extends StatelessWidget {
  const LogInFormLogoWidget({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(top: 15),
      child: Center(
        child: Image.asset("assets/images/logo.png"),
      ),
    );
  }
}
