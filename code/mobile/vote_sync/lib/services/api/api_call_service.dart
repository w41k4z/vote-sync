import 'package:dio/dio.dart';
import 'package:vote_sync/filter/auth_interceptor.dart';

class ApiCallService {
  final Dio _dio = Dio();

  ApiCallService() {
    _dio.options.baseUrl = 'http://localhost:8081';
    _dio.interceptors.add(AuthInterceptor());
  }

  Future<Response<T>> getCall<T>(String path) async {
    return await _dio.get<T>(path);
  }

  Future<Response<T>> postCall<T>(String path, dynamic data) async {
    return await _dio.post<T>(path, data: data);
  }
}
