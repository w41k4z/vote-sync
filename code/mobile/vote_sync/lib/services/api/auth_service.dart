import 'package:vote_sync/config/endpoints.dart';
import 'package:vote_sync/services/api/api_call_service.dart';

class AuthService extends ApiCallService {
  Future<String> authenticateUser(String identifier, String password) async {
    final response = await postCall(Endpoints.AUTH, {
      'identifier': identifier,
      'password': password,
    });
    String accessToken = response.data["payload"]["accessToken"];
    return accessToken;
  }
}
