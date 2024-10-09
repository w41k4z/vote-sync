package internship.project.election.service.domain;

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

    public List<Region> getRegionsWithoutGeoJson() {
        return this.regionRepository.findAllWithoutGeoJson();
    }

    public List<District> getDistrictsWithoutGeoJson() {
        return this.districtRepository.findAllWithoutGeoJson();
    }

    public List<District> getDistrictsByRegionIdWithoutGeoJson(Integer regionId) {
        return this.districtRepository.findByUpperDivisionIdWithoutGeoJson(regionId);
    }

    public List<Commune> getCommunesWithoutGeoJson() {
        return this.communeRepository.findAllWithoutGeoJson();
    }

    public List<Commune> getCommunesByDistrictIdWithoutGeoJson(Integer districtId) {
        return this.communeRepository.findByUpperDivisionIdWithoutGeoJson(districtId);
    }

    public List<Fokontany> getFokontanyWithoutGeoJson() {
        return this.fokontanyRepository.findAllWithoutGeoJson();
    }

    public List<Fokontany> getFokontanyByCommuneIdWithoutGeoJson(Integer communeId) {
        return this.fokontanyRepository.findByUpperDivisionIdWithoutGeoJson(communeId);
    }
}
