package ceni.system.votesync.service.entity.result;

import org.springframework.data.jpa.domain.Specification;

import ceni.system.votesync.model.base.view.result.ElectoralResult;
import ceni.system.votesync.model.entity.election.result.ResultImage;
import ceni.system.votesync.model.view.election.result.CommunalResult;
import ceni.system.votesync.model.view.election.result.DistrictResult;
import ceni.system.votesync.model.view.election.result.FokontanyLocalElectionResult;
import ceni.system.votesync.model.view.election.result.FokontanyResult;
import ceni.system.votesync.model.view.election.result.MunicipalResult;
import ceni.system.votesync.model.view.election.result.PollingStationLocalElectionResult;
import ceni.system.votesync.model.view.election.result.PollingStationResult;

public class ElectoralResultSpecification {

        public static <T extends ElectoralResult> Specification<T> withElectionId(Integer electionId) {
                return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("electionId"),
                                electionId);
        }

        public static Specification<ResultImage> imagesWithResultId(Integer resultId) {
                return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("resultId"),
                                resultId);
        }

        public static Specification<PollingStationResult> filterPollingStationResult(
                        Integer regionId, Integer districtId, Integer communeId, Integer fokontanyId) {
                Specification<PollingStationResult> spec = Specification.where(null);
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

        public static Specification<PollingStationLocalElectionResult> filterPollingStationLocalResult(
                        Integer regionId, Integer districtId, Integer municipalityId, Integer fokontanyId) {
                Specification<PollingStationLocalElectionResult> spec = Specification.where(null);
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

        public static Specification<FokontanyResult> filterFokontanyResult(
                        Integer regionId, Integer districtId, Integer communeId) {
                Specification<FokontanyResult> spec = Specification.where(null);
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

        public static Specification<FokontanyLocalElectionResult> filterFokontanyLocalResult(
                        Integer regionId, Integer districtId, Integer municipalityId) {
                Specification<FokontanyLocalElectionResult> spec = Specification.where(null);
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

        public static Specification<CommunalResult> filterCommunalResult(
                        Integer regionId, Integer districtId) {
                Specification<CommunalResult> spec = Specification.where(null);
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

        public static Specification<MunicipalResult> filterMunicipalResult(
                        Integer regionId, Integer districtId) {
                Specification<MunicipalResult> spec = Specification.where(null);
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

        public static Specification<DistrictResult> filterDistrictResult(
                        Integer regionId) {
                Specification<DistrictResult> spec = Specification.where(null);
                if (regionId != null) {
                        spec = spec.and(
                                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("regionId"),
                                                        regionId));
                }
                return spec;
        }
}
