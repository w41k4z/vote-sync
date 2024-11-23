package ceni.system.votesync.service.entity.alert;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.model.view.VAlert;

public class AlertSpecification {
    public static Specification<VAlert> withElectionId(Integer electionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("electionId"),
                electionId);
    }

    public static Specification<VAlert> filterPollingStationResult(
            Integer regionId, Integer districtId, Integer communeId, Integer fokontanyId) {
        Specification<VAlert> spec = Specification.where(null);
        if (regionId != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regionId"),
                            regionId));
        }
        if (districtId != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                            root.get("districtId"),
                            districtId));
        }
        if (communeId != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("communeId"),
                            communeId));
        }
        if (fokontanyId != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fokontanyId"),
                            fokontanyId));
        }
        return spec;
    }
}
