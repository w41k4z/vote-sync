import 'package:vote_sync/config/endpoints.dart';
import 'package:vote_sync/dto/request/auth/auth_request.dart';
import 'package:vote_sync/services/api/api_call_service.dart';

class AuthService extends ApiCallService {
  void authenticateUser(String identifier, String password) async {
    AuthRequest authRequest = AuthRequest(
      identifier: identifier,
      password: password,
    );
    await postCall(Endpoints.AUTH, authRequest);
  }
}
