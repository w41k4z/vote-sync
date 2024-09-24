package internship.project.election.service.impl.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import internship.project.election.model.view.VPollingStation;

public final class PollingStationSpecification {

    public static Specification<VPollingStation> filterPollingStations(Integer regionId, Integer districtId,
            Integer communeId,
            Integer fokontanyId) {
        Specification<VPollingStation> spec = Specification.where(null);
        if (regionId != null) {
            spec = spec.and(getPollingStationsByRegionId(regionId));
        }
        if (districtId != null) {
            spec = spec.and(getPollingStationsByDistrictId(districtId));
        }
        if (communeId != null) {
            spec = spec.and(getPollingStationsByCommuneId(communeId));
        }
        if (fokontanyId != null) {
            spec = spec.and(getPollingStationsByFokontanyId(fokontanyId));
        }
        return spec;
    }

    public static Specification<VPollingStation> getPollingStationsByRegionId(Integer regionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regionId"), regionId);
    }

    public static Specification<VPollingStation> getPollingStationsByDistrictId(Integer districtId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("districtId"), districtId);
    }

    public static Specification<VPollingStation> getPollingStationsByCommuneId(Integer communeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("communeId"), communeId);
    }

    public static Specification<VPollingStation> getPollingStationsByFokontanyId(Integer fokontanyId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fokontanyId"), fokontanyId);
    }
}
