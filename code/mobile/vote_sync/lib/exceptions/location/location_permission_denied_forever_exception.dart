import 'package:vote_sync/exceptions/location/location_permission_denied_exception.dart';

class LocationPermissionDeniedForeverException
    extends LocationPermissionDeniedException {
  const LocationPermissionDeniedForeverException();

  @override
  String toString() =>
      'La permission d\'accès à la localisation a été refusée pour toujours.';
}
