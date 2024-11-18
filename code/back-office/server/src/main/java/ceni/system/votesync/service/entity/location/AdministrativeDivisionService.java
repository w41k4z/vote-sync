package ceni.system.votesync.service.entity.location;

import java.util.List;

import org.springframework.stereotype.Service;

import ceni.system.votesync.model.entity.location.Commune;
import ceni.system.votesync.model.entity.location.District;
import ceni.system.votesync.model.entity.location.Fokontany;
import ceni.system.votesync.model.entity.location.Municipality;
import ceni.system.votesync.model.entity.location.MunicipalityDistrict;
import ceni.system.votesync.model.entity.location.MunicipalityFokontany;
import ceni.system.votesync.model.entity.location.Region;
import ceni.system.votesync.repository.entity.location.CommuneRepository;
import ceni.system.votesync.repository.entity.location.DistrictRepository;
import ceni.system.votesync.repository.entity.location.FokontanyRepository;
import ceni.system.votesync.repository.entity.location.MunicipalityDistrictRepository;
import ceni.system.votesync.repository.entity.location.MunicipalityFokontanyRepository;
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
    private MunicipalityFokontanyRepository municipalityFokontanyRepository;

    public AdministrativeDivisionService(RegionRepository regionRepository,
            DistrictRepository districtRepository,
            MunicipalityDistrictRepository municipalityDistrictRepository,
            MunicipalityRepository municipalityRepository,
            CommuneRepository communeRepository,
            FokontanyRepository fokontanyRepository,
            MunicipalityFokontanyRepository municipalityFokontanyRepository) {
        this.regionRepository = regionRepository;
        this.districtRepository = districtRepository;
        this.municipalityDistrictRepository = municipalityDistrictRepository;
        this.municipalityRepository = municipalityRepository;
        this.communeRepository = communeRepository;
        this.fokontanyRepository = fokontanyRepository;
        this.municipalityFokontanyRepository = municipalityFokontanyRepository;
    }

    public List<Region> getRegions() {
        return this.regionRepository.findAll();
    }

    public List<District> getDistricts() {
        return this.districtRepository.findAll();
    }

    public List<District> getDistrictsByRegionId(Integer regionId) {
        return this.districtRepository.findByUpperDivisionId(regionId);
    }

    public List<MunicipalityDistrict> getMunicipalityDistricts() {
        return this.municipalityDistrictRepository.findAll();
    }

    public List<MunicipalityDistrict> getMunicipalityDistrictsByRegionId(Integer regionId) {
        return this.municipalityDistrictRepository.findByUpperDivisionId(regionId);
    }

    public List<Municipality> getMunicipalities() {
        return this.municipalityRepository.findAll();
    }

    public List<Municipality> getMunicipalitiesByDistrictId(Integer districtId) {
        return this.municipalityRepository.findByUpperDivisionId(districtId);
    }

    public List<Commune> getCommunes() {
        return this.communeRepository.findAll();
    }

    public List<Commune> getCommunesByDistrictId(Integer districtId) {
        return this.communeRepository.findByUpperDivisionId(districtId);
    }

    public List<Fokontany> getFokontany() {
        return this.fokontanyRepository.findAll();
    }

    public List<Fokontany> getFokontanyByCommuneId(Integer communeId) {
        return this.fokontanyRepository.findByUpperDivisionId(communeId);
    }

    public List<MunicipalityFokontany> getMunicipalityFokontanyByMunicipalityId(Integer municipalityId) {
        return this.municipalityFokontanyRepository.findByUpperDivisionId(municipalityId);
    }

    public List<MunicipalityFokontany> getMunicipalityFokontany() {
        return this.municipalityFokontanyRepository.findAll();
    }
}
