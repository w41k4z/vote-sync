package ceni.system.votesync.service.entity.location;

import java.util.List;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.location.Commune;
import ceni.system.votesync.model.entity.location.District;
import ceni.system.votesync.model.entity.location.Fokontany;
import ceni.system.votesync.model.entity.location.Municipality;
import ceni.system.votesync.model.entity.location.MunicipalityDistrict;
import ceni.system.votesync.model.entity.location.Region;
import ceni.system.votesync.repository.entity.location.CommuneRepository;
import ceni.system.votesync.repository.entity.location.DistrictRepository;
import ceni.system.votesync.repository.entity.location.FokontanyRepository;
import ceni.system.votesync.repository.entity.location.MunicipalityDistrictRepository;
import ceni.system.votesync.repository.entity.location.MunicipalityRepository;
import ceni.system.votesync.repository.entity.location.RegionRepository;

@Service
public class AdministrativeDivisionService {

    private RegionRepository regionRepository;
    private DistrictRepository districtRepository;
    private MunicipalityDistrictRepository municipalityDistrictRepository;
    private MunicipalityRepository municipalityRepository;
    private CommuneRepository communeRepository;
    private FokontanyRepository fokontanyRepository;

    public AdministrativeDivisionService(RegionRepository regionRepository,
            DistrictRepository districtRepository,
            MunicipalityDistrictRepository municipalityDistrictRepository,
            MunicipalityRepository municipalityRepository,
            CommuneRepository communeRepository,
            FokontanyRepository fokontanyRepository) {
        this.regionRepository = regionRepository;
        this.districtRepository = districtRepository;
        this.municipalityDistrictRepository = municipalityDistrictRepository;
        this.municipalityRepository = municipalityRepository;
        this.communeRepository = communeRepository;
        this.fokontanyRepository = fokontanyRepository;
    }

    public List<Region> getRegions() {
        return this.regionRepository.findAll();
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

    public List<MunicipalityDistrict> getMunicipalityDistrictsWithoutGeoJson() {
        return this.municipalityDistrictRepository.findAllWithoutGeoJson();
    }

    public List<MunicipalityDistrict> getMunicipalityDistrictsByRegionIdWithoutGeoJson(Integer regionId) {
        return this.municipalityDistrictRepository.findByUpperDivisionIdWithoutGeoJson(regionId);
    }

    public List<Municipality> getMunicipalitiesWithoutGeoJson() {
        return this.municipalityRepository.findAllWithoutGeoJson();
    }

    public List<Municipality> getMunicipalitiesByDistrictIdWithoutGeoJson(Integer districtId) {
        return this.municipalityRepository.findByUpperDivisionIdWithoutGeoJson(districtId);
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
