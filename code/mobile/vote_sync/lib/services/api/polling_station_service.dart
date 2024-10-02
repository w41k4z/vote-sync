import 'package:geolocator/geolocator.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/dto/response/payload/polling_station_payload.dart';
import 'package:vote_sync/services/api/api_call_service.dart';
import 'package:vote_sync/services/location_service.dart';

class PollingStationService extends ApiCallService {
  Future<void> getNearestPollingStation() async {
    LocationService locationService = GetIt.I.get<LocationService>();
    Position currentPosition = await locationService.getCurrentLocation();
    final response =
        await postCall<PollingStationPayload>('polling-station/nearest', {
      'latitude': currentPosition.latitude,
      'longitude': currentPosition.longitude,
    });
    print(response.data);
  }
}
