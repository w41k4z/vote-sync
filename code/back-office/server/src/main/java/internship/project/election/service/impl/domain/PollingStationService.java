package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import internship.project.election.dto.request.NearestPollingStationRequest;
import internship.project.election.model.PollingStation;
import internship.project.election.model.view.VPollingStation;
import internship.project.election.repository.PollingStationRepository;
import internship.project.election.repository.view.VPollingStationRepository;
import internship.project.election.service.impl.domain.specification.PollingStationSpecification;

@Service
public class PollingStationService {

    private PollingStationRepository pollingStationRepository;
    private VPollingStationRepository viewRepository;

    public PollingStationService(
            PollingStationRepository pollingStationRepository, VPollingStationRepository viewRepository) {
        this.pollingStationRepository = pollingStationRepository;
        this.viewRepository = viewRepository;
    }

    public PagedModel<VPollingStation> getAllPollingStation(Integer regionId, Integer districtId, Integer communeId,
            Integer fokontanyId, Pageable pageable) {
        Specification<VPollingStation> spec = PollingStationSpecification.filterPollingStations(regionId, districtId,
                communeId, fokontanyId);
        return new PagedModel<>(this.viewRepository.findAll(spec, pageable));
    }

    public List<PollingStation> getNearestPollingStations(NearestPollingStationRequest request) {
        return this.pollingStationRepository.findNearbyPollingStations(request.getLatitude(), request.getLongitude(),
                request.getRange());
    }
}
