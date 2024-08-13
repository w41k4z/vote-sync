import 'package:dartz/dartz.dart';
import '../../../../core/error/failures.dart';
import '../entities/authentication_response.dart';

abstract class AuthenticationRepository {
  Future<Either<Failure, AuthenticationResponse>> authenticate(
      String? location, String? password);
}
