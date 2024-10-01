import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:vote_sync/dto/request/auth/auth_request.dart';

class ApiCallService {
  Future<T> getCall<T>(String url, AuthRequest authRequest) async {
    final response = await http.get(Uri.parse(url));
    return response.body as T;
  }

  Future<T> postCall<T>(String url, dynamic body) async {
    final response = await http.post(Uri.parse(url),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(body));
    return response.body as T;
  }
}
