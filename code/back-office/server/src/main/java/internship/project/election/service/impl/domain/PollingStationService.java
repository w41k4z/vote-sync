package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.view.VPollingStation;
import internship.project.election.repository.view.VPollingStationRepository;

@Service
public class PollingStationService {

    private VPollingStationRepository viewRepository;

    public PollingStationService(VPollingStationRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    public List<VPollingStation> getAllPollingStation() {
        return this.viewRepository.findAll();
    }
}
