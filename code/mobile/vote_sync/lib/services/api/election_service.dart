import 'package:vote_sync/config/endpoints.dart';
import 'package:vote_sync/dto/election_dto.dart';
import 'package:vote_sync/services/api/api_call_service.dart';

class ElectionService extends ApiCallService {
  final String electionEndpoint = Endpoints.ELECTIONS;

  Future<List<ElectionDTO>> getCurrentElections() async {
    final response = await getCall('$electionEndpoint/current');
    final payload = response.data["payload"];
    List<ElectionDTO> elections = [];
    payload["elections"].forEach((rawElection) {
      ElectionDTO election = ElectionDTO(
        rawElection["id"],
        rawElection["type"]["type"],
        rawElection["name"],
        rawElection["startDate"],
      );
      elections.add(election);
    });
    return elections;
  }
}
