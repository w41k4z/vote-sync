import 'package:get_it/get_it.dart';
import 'package:vote_sync/config/endpoints.dart';
import 'package:vote_sync/services/api/api_call_service.dart';
import 'package:vote_sync/services/token_service.dart';

class AuthService extends ApiCallService {
  String? _accessToken;

  AuthService(String? accessToken) {
    _accessToken = accessToken;
  }

  String getToken() {
    // Dart does not support to return a String even after null check
    // So care to call isAuthenticated() before calling getToken() to expect a non-null value
    return _accessToken ?? '';
  }

  Future<void> authenticateUser(String identifier, String password) async {
    final response = await postCall(Endpoints.AUTH, {
      'identifier': identifier,
      'password': password,
    });
    String accessToken = response.data["payload"]["accessToken"];
    await GetIt.I.get<TokenService>().saveToken(accessToken);
    _accessToken = accessToken;
  }

  bool isAuthenticated() {
    return _accessToken != null;
  }

  Future<void> logout() async {
    await GetIt.I.get<TokenService>().clearToken();
    _accessToken = null;
  }
}
