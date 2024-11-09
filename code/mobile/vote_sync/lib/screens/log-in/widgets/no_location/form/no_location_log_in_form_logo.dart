import 'package:flutter/widgets.dart';

class NoLocationLogInFormLogoWidget extends StatelessWidget {
  const NoLocationLogInFormLogoWidget({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(top: 15),
      child: Center(
        child: Image.asset(
          "assets/images/logo-light.png",
        ),
      ),
    );
  }
}
