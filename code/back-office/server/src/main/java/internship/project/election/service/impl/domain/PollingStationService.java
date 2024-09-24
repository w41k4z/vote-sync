package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import internship.project.election.model.view.VPollingStation;
import internship.project.election.repository.view.VPollingStationRepository;
import internship.project.election.service.impl.domain.specification.PollingStationSpecification;

@Service
public class PollingStationService {

    private VPollingStationRepository viewRepository;

    public PollingStationService(VPollingStationRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    public List<VPollingStation> getAllPollingStation(Integer regionId, Integer districtId, Integer communeId,
            Integer fokontanyId) {
        Specification<VPollingStation> spec = PollingStationSpecification.filterPollingStations(regionId, districtId,
                communeId, fokontanyId);
        return this.viewRepository.findAll(spec);
    }
}
