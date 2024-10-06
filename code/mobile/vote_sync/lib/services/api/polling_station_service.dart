import 'package:geolocator/geolocator.dart';
import 'package:get_it/get_it.dart';
import 'package:vote_sync/config/endpoints.dart';
import 'package:vote_sync/dto/polling_station_dto.dart';
import 'package:vote_sync/dto/election_dto.dart';
import 'package:vote_sync/services/api/api_call_service.dart';
import 'package:vote_sync/services/location_service.dart';

class PollingStationService extends ApiCallService {
  final String pollingStationEndpoint = Endpoints.POLLING_STATIONS;

  Future<List<dynamic>> getNearestPollingStationAndCurrentElections() async {
    LocationService locationService = GetIt.I.get<LocationService>();
    Position currentPosition = await locationService.getCurrentLocation();
    final response = await postCall('$pollingStationEndpoint/nearest', {
      'latitude': currentPosition.latitude,
      'longitude': currentPosition.longitude,
    });
    final payload = response.data["payload"];
    List<PollingStationDTO> nearestPollingStations = [];
    List<ElectionDTO> currentElections = [];
    payload["pollingStations"].forEach((rawPollingStation) {
      PollingStationDTO pollingStation = PollingStationDTO(
          rawPollingStation["id"],
          rawPollingStation["code"],
          rawPollingStation["voteCenterId"],
          rawPollingStation["name"]);
      nearestPollingStations.add(pollingStation);
    });
    payload["elections"].forEach((rawElection) {
      ElectionDTO election = ElectionDTO(
        rawElection["id"],
        rawElection["type"]["type"],
        rawElection["name"],
        rawElection["startDate"],
      );
      currentElections.add(election);
    });
    return [nearestPollingStations, currentElections];
  }

  Future<Map<String, dynamic>> getPollingStationData(
      int pollingStationId, int electionId) async {
    final response = await getCall(
        '$pollingStationEndpoint/data/$electionId/$pollingStationId');
    return response.data["payload"];
  }
}
