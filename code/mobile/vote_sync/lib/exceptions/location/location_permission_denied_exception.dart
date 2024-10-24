class LocationPermissionDeniedException implements Exception {
  const LocationPermissionDeniedException();

  @override
  String toString() =>
      'La permission d\'accès à la localisation a été refusée.';
}
