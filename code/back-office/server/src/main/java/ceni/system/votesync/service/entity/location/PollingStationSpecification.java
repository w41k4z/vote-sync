package ceni.system.votesync.service.entity.location;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.model.view.VPollingStation;

public final class PollingStationSpecification {

    public static Specification<VPollingStation> filterPollingStations(Integer regionId, Integer districtId,
            Integer communeId,
            Integer fokontanyId) {
        Specification<VPollingStation> spec = Specification.where(null);
        if (regionId != null) {
            spec = spec.and(withRegionId(regionId));
        }
        if (districtId != null) {
            spec = spec.and(withDistrictId(districtId));
        }
        if (communeId != null) {
            spec = spec.and(withCommuneId(communeId));
        }
        if (fokontanyId != null) {
            spec = spec.and(withFokontanyId(fokontanyId));
        }
        return spec;
    }

    public static Specification<VPollingStation> withRegionId(Integer regionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regionId"), regionId);
    }

    public static Specification<VPollingStation> withDistrictId(Integer districtId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("districtId"), districtId);
    }

    public static Specification<VPollingStation> withCommuneId(Integer communeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("communeId"), communeId);
    }

    public static Specification<VPollingStation> withFokontanyId(Integer fokontanyId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fokontanyId"), fokontanyId);
    }
}
