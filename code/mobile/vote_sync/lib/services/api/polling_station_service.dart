import 'package:geolocator/geolocator.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/services/api/api_call_service.dart';
import 'package:vote_sync/services/location_service.dart';

class PollingStationService extends ApiCallService {
  Future<void> getNearestPollingStation() async {
    LocationService locationService = GetIt.I.get<LocationService>();
    Position currentPosition = await locationService.getCurrentLocation();
    final response = await getCall('polling-station/nearest');
  }
}
