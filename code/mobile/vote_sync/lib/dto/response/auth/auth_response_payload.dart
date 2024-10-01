class AuthResponsePayload {
  final String accessToken;
  final String refreshToken;

  AuthResponsePayload({required this.accessToken, required this.refreshToken});

  factory AuthResponsePayload.fromJson(Map<String, dynamic> json) {
    return AuthResponsePayload(
      accessToken: json['token'],
      refreshToken: json['refreshToken'],
    );
  }
}
