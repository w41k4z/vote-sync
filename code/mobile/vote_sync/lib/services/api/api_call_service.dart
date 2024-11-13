import 'package:dio/dio.dart';
import 'package:vote_sync/env.dart';
import 'package:vote_sync/filters/auth_interceptor.dart';

class ApiCallService {
  final Dio _dio = Dio();

  ApiCallService() {
    _dio.options.baseUrl = Env.BASE_URL;
    _dio.interceptors.add(AuthInterceptor());
  }

  Future<Response> getCall(String path, {Options? options}) async {
    return await _dio.get(path, options: options);
  }

  Future<Response> postCall(
    String path,
    dynamic data, {
    Options? options,
  }) async {
    return await _dio.post(path, data: data, options: options);
  }

  Future<Response> downloadFile(String path, String destinationPath) async {
    return await _dio.download(path, destinationPath);
  }
}
