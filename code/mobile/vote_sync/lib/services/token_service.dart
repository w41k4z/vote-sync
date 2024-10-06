import 'package:get_it/get_it.dart';
import 'package:vote_sync/services/secure_storage_service.dart';

class TokenService {
  static const _tokenKey = "accessToken";

  Future<void> saveToken(String token) async {
    await GetIt.I.get<SecureStorageService>().save(_tokenKey, token);
  }

  Future<String?> getToken() async {
    return await GetIt.I.get<SecureStorageService>().read(_tokenKey);
  }

  Future<void> clearToken() async {
    await GetIt.I.get<SecureStorageService>().delete(_tokenKey);
  }
}
