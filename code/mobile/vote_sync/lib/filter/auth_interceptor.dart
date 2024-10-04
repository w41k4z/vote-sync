import 'dart:io';

import 'package:dio/dio.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/services/api/auth_service.dart';

class AuthInterceptor extends Interceptor {
  final List<String> _excludePaths = ['/auth/**'];

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {
    AuthService authService = GetIt.I.get<AuthService>();

    String path = options.uri.toString();
    if (!_excludePaths.any((excludedPath) => path.contains(excludedPath))) {
      if (authService.isAuthenticated()) {
        String token = authService.getToken();
        options.headers[HttpHeaders.authorizationHeader] = 'Bearer $token';
      }
    }
    super.onRequest(options, handler);
  }
}
