package internship.project.election.service.impl.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import internship.project.election.model.location.AdministrationDivision;
import internship.project.election.model.location.Commune;
import internship.project.election.model.location.District;
import internship.project.election.model.location.Fokontany;
import internship.project.election.model.location.Region;
import internship.project.election.repository.location.CommuneRepository;
import internship.project.election.repository.location.DistrictRepository;
import internship.project.election.repository.location.FokontanyRepository;
import internship.project.election.repository.location.RegionRepository;

@Service
public class AdministrativeDivisionService {

    private RegionRepository regionRepository;
    private DistrictRepository districtRepository;
    private CommuneRepository communeRepository;
    private FokontanyRepository fokontanyRepository;

    public AdministrativeDivisionService(RegionRepository regionRepository,
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

    public List<AdministrationDivision> getRegionsWithoutGeoJson() {
        return this.regionRepository.findAllWithoutGeoJson();
    }

    public List<District> getDistrictsStat() {
        return this.districtRepository.findAll();
    }

    public List<AdministrationDivision> getDistrictsWithoutGeoJson() {
        return this.districtRepository.findAllWithoutGeoJson();
    }

    public List<District> getDistrictsByRegionId(Integer regionId) {
        return this.districtRepository.findByUpperDivisionId(regionId);
    }

    public List<Commune> getCommunesStat() {
        return this.communeRepository.findAll();
    }

    public List<AdministrationDivision> getCommunesWithoutGeoJson() {
        return this.communeRepository.findAllWithoutGeoJson();
    }

    public List<Commune> getCommunesByDistrictId(Integer districtId) {
        return this.communeRepository.findByUpperDivisionId(districtId);
    }

    public List<Fokontany> getFokontanyStat() {
        return this.fokontanyRepository.findAll();
    }

    public List<AdministrationDivision> getFokontanyWithoutGeoJson() {
        return this.fokontanyRepository.findAllWithoutGeoJson();
    }

    public List<Fokontany> getFokontanyByCommuneId(Integer communeId) {
        return this.fokontanyRepository.findByUpperDivisionId(communeId);
    }
}
