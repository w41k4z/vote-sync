class ApiResponse<T> {
  T payload;
  int statusCode;
  String message;

  ApiResponse({
    required this.payload,
    required this.statusCode,
    required this.message,
  });
}
