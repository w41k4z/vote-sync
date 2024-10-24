class DeviceLocationServiceDisabledException implements Exception {
  const DeviceLocationServiceDisabledException();

  @override
  String toString() =>
      'La service de localisation sur l\'appareil est désactivé.';
}
