import 'package:dio/dio.dart';
import 'package:vote_sync/config/env.dart';
import 'package:vote_sync/filters/auth_interceptor.dart';

class ApiCallService {
  final Dio _dio = Dio();

  ApiCallService() {
    _dio.options.baseUrl = Env.BASE_URL;
    _dio.interceptors.add(AuthInterceptor());
  }

  Future<Response> getCall(String path) async {
    return await _dio.get(path);
  }

  Future<Response> postCall(String path, dynamic data) async {
    return await _dio.post(path, data: data);
  }

  Future<Response> downloadFile(String path, String destinationPath) async {
    return await _dio.download(path, destinationPath);
  }
}
