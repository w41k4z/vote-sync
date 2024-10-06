import 'dart:io';

import 'package:dio/dio.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/services/app_instance.dart';

class AuthInterceptor extends Interceptor {
  final List<String> _excludePaths = ['/auth/**'];

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {
    AppInstance appInstance = GetIt.I.get<AppInstance>();

    String path = options.uri.toString();
    if (!_excludePaths.any((excludedPath) => path.contains(excludedPath))) {
      if (appInstance.isUserAuthenticated()) {
        String token = appInstance.getToken();
        options.headers[HttpHeaders.authorizationHeader] = 'Bearer $token';
      }
    }
    super.onRequest(options, handler);
  }
}
