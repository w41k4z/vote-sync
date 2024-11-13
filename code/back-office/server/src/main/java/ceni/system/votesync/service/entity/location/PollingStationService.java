package ceni.system.votesync.service.entity.location;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import ceni.system.votesync.dto.request.NearestPollingStationRequest;
import ceni.system.votesync.exception.PollingStationNotFoundException;
import ceni.system.votesync.model.entity.PollingStation;
import ceni.system.votesync.model.view.VPollingStation;
import ceni.system.votesync.repository.entity.PollingStationRepository;
import ceni.system.votesync.repository.view.VPollingStationRepository;

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

    public Optional<VPollingStation> getPollingStationById(Integer id) {
        return this.viewRepository.findById(id);
    }

    public PollingStation getPollingStationByCode(String code) {
        PollingStation pollingStation = new PollingStation();
        pollingStation.setCode(code);
        return this.pollingStationRepository.findOne(Example.of(pollingStation)).orElseThrow(
                () -> new PollingStationNotFoundException("Polling station with code: " + code + ", not found"));
    }
}
