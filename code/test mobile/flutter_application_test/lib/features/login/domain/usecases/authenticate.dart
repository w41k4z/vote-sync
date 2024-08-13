import 'package:dartz/dartz.dart';
import 'package:flutter_application_test/core/error/failures.dart';
import 'package:flutter_application_test/features/login/domain/entities/authentication_response.dart';

import '../repositories/authentication_repository.dart';

class Authenticate {
  final AuthenticationRepository repository;

  Authenticate(this.repository);

  Future<Either<Failure, AuthenticationResponse>> call(
      {required String location, required String password}) async {
    return await repository.authenticate(location, password);
  }
}
