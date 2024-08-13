import 'package:flutter_application_test/features/login/domain/entities/voter.dart';

class AuthenticationResponse {
  List<Voter> voters;

  AuthenticationResponse({this.voters = const []});
}
