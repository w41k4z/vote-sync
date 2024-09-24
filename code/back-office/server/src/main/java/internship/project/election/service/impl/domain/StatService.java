package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.location.Commune;
import internship.project.election.model.location.District;
import internship.project.election.model.location.Fokontany;
import internship.project.election.model.location.Region;
import internship.project.election.repository.location.CommuneRepository;
import internship.project.election.repository.location.DistrictRepository;
import internship.project.election.repository.location.FokontanyRepository;
import internship.project.election.repository.location.RegionRepository;

@Service
public class StatService {

    private RegionRepository regionRepository;
    private DistrictRepository districtRepository;
    private CommuneRepository communeRepository;
    private FokontanyRepository fokontanyRepository;

    public StatService(RegionRepository regionRepository,
            DistrictRepository districtRepository,
            CommuneRepository communeRepository,
            FokontanyRepository fokontanyRepository) {
        this.regionRepository = regionRepository;
        this.districtRepository = districtRepository;
        this.communeRepository = communeRepository;
        this.fokontanyRepository = fokontanyRepository;
    }

    public List<Region> getRegionsStat() {
        return this.regionRepository.findAll();
    }

    public List<District> getDistrictsStat() {
        return this.districtRepository.findAll();
    }

    public List<Commune> getCommunesStat() {
        return this.communeRepository.findAll();
    }

    public List<Fokontany> getFokontanyStat() {
        return this.fokontanyRepository.findAll();
    }
}
