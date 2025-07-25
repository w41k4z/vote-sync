package ceni.system.votesync.service.entity.result;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.model.base.entity.election.result.provisional.ProvisionalElectoralResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalCommunalResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalDistrictResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalFokontanyResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalMunicipalResult;
import ceni.system.votesync.model.entity.election.result.provisional.ProvisionalPollingStationResult;

public class ProvisionalElectoralResultSpecification {

        public static <T extends ProvisionalElectoralResult> Specification<T> withElectionId(Integer electionId) {
                return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("electionId"),
                                electionId);
        }

        public static Specification<ProvisionalPollingStationResult> filterProvisionalPollingStationResult(
                        Integer regionId, Integer districtId, Integer communeId, Integer fokontanyId) {
                Specification<ProvisionalPollingStationResult> spec = Specification.where(null);
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

        public static Specification<ProvisionalPollingStationResult> filterProvisionalPollingStationLocalResult(
                        Integer regionId, Integer districtId, Integer municipalityId, Integer fokontanyId) {
                Specification<ProvisionalPollingStationResult> spec = Specification.where(null);
                if (regionId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regionId"),
                                                        regionId));
                }
                if (districtId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                                                        root.get("municipalityDistrictId"),
                                                        districtId));
                }
                if (municipalityId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                                                        root.get("municipalityId"),
                                                        municipalityId));
                }
                if (fokontanyId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fokontanyId"),
                                                        fokontanyId));
                }
                return spec;
        }

        public static Specification<ProvisionalFokontanyResult> filterProvisionalFokontanyResult(
                        Integer regionId, Integer districtId, Integer communeId) {
                Specification<ProvisionalFokontanyResult> spec = Specification.where(null);
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
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                                                        root.get("communeId"),
                                                        communeId));
                }
                return spec;
        }

        public static Specification<ProvisionalFokontanyResult> filterProvisionalFokontanyLocalResult(
                        Integer regionId, Integer districtId, Integer municipalityId) {
                Specification<ProvisionalFokontanyResult> spec = Specification.where(null);
                if (regionId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regionId"),
                                                        regionId));
                }
                if (districtId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                                                        root.get("municipalityDistrictId"),
                                                        districtId));
                }
                if (municipalityId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                                                        root.get("municipalityId"),
                                                        municipalityId));
                }
                return spec;
        }

        public static Specification<ProvisionalCommunalResult> filterProvisionalCommunalResult(
                        Integer regionId, Integer districtId) {
                Specification<ProvisionalCommunalResult> spec = Specification.where(null);
                if (regionId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regionId"),
                                                        regionId));
                }
                if (districtId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("districtId"),
                                                        districtId));
                }
                return spec;
        }

        public static Specification<ProvisionalMunicipalResult> filterProvisionalMunicipalResult(
                        Integer regionId, Integer districtId) {
                Specification<ProvisionalMunicipalResult> spec = Specification.where(null);
                if (regionId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regionId"),
                                                        regionId));
                }
                if (districtId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("districtId"),
                                                        districtId));
                }
                return spec;
        }

        public static Specification<ProvisionalDistrictResult> filterProvisionalDistrictResult(
                        Integer regionId) {
                Specification<ProvisionalDistrictResult> spec = Specification.where(null);
                if (regionId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regionId"),
                                                        regionId));
                }
                return spec;
        }
}
