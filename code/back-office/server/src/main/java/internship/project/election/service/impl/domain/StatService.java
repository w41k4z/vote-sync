package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.location.Region;
import internship.project.election.repository.RegionRepository;

@Service
public class StatService {

    private RegionRepository regionRepository;

    public StatService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegionsStat() {
        return this.regionRepository.findAll();
    }
}
