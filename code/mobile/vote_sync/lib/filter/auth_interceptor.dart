import 'dart:io';

import 'package:dio/dio.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/services/token_service.dart';

class AuthInterceptor extends Interceptor {
  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {
    TokenService tokenService = GetIt.I.get<TokenService>();
    tokenService.getToken().then((token) {
      if (token != null) {
        options.headers[HttpHeaders.authorizationHeader] = 'Bearer $token';
      }
      super.onRequest(options, handler);
    });
  }
}
