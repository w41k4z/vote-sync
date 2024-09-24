package internship.project.election.service.impl.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
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

    public PagedModel<VPollingStation> getAllPollingStation(Integer regionId, Integer districtId, Integer communeId,
            Integer fokontanyId, Pageable pageable) {
        Specification<VPollingStation> spec = PollingStationSpecification.filterPollingStations(regionId, districtId,
                communeId, fokontanyId);
        return new PagedModel<>(this.viewRepository.findAll(spec, pageable));
    }
}
