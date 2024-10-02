import 'package:dio/dio.dart';
import 'package:vote_sync/config/endpoints.dart';
import 'package:vote_sync/filter/auth_interceptor.dart';

class ApiCallService {
  final Dio _dio = Dio();

  ApiCallService() {
    _dio.options.baseUrl = Endpoints.BASE_URL;
    _dio.interceptors.add(AuthInterceptor());
  }

  Future<Response> getCall(String path) async {
    return await _dio.get(path);
  }

  Future<Response> postCall(String path, dynamic data) async {
    return await _dio.post(path, data: data);
  }
}
