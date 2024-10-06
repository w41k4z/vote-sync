import 'package:get_it/get_it.dart';
import 'package:vote_sync/services/secure_storage_service.dart';
import 'package:vote_sync/services/token_service.dart';

class AppInstance {
  String? _accessToken;
  String? _pollingStationId;
  String? _electionId;

  AppInstance(
      String? accessToken, String? pollingStationId, String? electionId) {
    _accessToken = accessToken;
    _pollingStationId = pollingStationId;
    _electionId = electionId;
  }

  bool isUserAuthenticated() {
    return _accessToken != null;
  }

  bool isPollingStationIdSet() {
    return _pollingStationId != null;
  }

  bool isElectionIdSet() {
    return _electionId != null;
  }

  Future<void> grantAccess(
      String accessToken, String pollingStationId, String electionId) async {
    await GetIt.I.get<TokenService>().saveToken(accessToken);
    await GetIt.I
        .get<SecureStorageService>()
        .save('pollingStationId', pollingStationId);
    await GetIt.I.get<SecureStorageService>().save('electionId', electionId);
    _accessToken = accessToken;
    _pollingStationId = pollingStationId;
    _electionId = electionId;
  }

  String getToken() {
    // Dart does not support to return a String even after null check
    // So care to call isUserAuthenticated() before calling getToken() to expect a non-null value
    return _accessToken ?? '';
  }

  String getPollingStationId() {
    // Dart does not support to return a String even after null check
    // So care to call isPollingStationIdSet() before calling getPollingStationId() to expect a non-null value
    return _pollingStationId ?? '';
  }

  String getElectionId() {
    // Dart does not support to return a String even after null check
    // So care to call isElectionIdSet() before calling getElectionId() to expect a non-null value
    return _electionId ?? '';
  }

  Future<void> logout() async {
    await GetIt.I.get<TokenService>().clearToken();
    _accessToken = null;
    _pollingStationId = null;
  }
}
