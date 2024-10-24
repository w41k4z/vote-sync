import 'package:geolocator/geolocator.dart';
import 'package:vote_sync/exceptions/location/device_location_service_disabled_exception.dart';
import 'package:vote_sync/exceptions/location/location_permission_denied_exception.dart';
import 'package:vote_sync/exceptions/location/location_permission_denied_forever_exception.dart';

class LocationService {
  Future<Position> getCurrentLocation() async {
    bool serviceEnabled;
    LocationPermission permission;

    serviceEnabled = await Geolocator.isLocationServiceEnabled();
    if (!serviceEnabled) {
      throw const DeviceLocationServiceDisabledException();
    }

    permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
      if (permission == LocationPermission.denied) {
        throw const LocationPermissionDeniedException();
      }
    }

    if (permission == LocationPermission.deniedForever) {
      throw const LocationPermissionDeniedForeverException();
    }

    return await Geolocator.getCurrentPosition();
  }
}
