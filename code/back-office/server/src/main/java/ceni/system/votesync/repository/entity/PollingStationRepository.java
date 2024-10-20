package ceni.system.votesync.repository.entity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ceni.system.votesync.model.entity.PollingStation;
import ceni.system.votesync.repository.base.EntityRepository;

public interface PollingStationRepository extends EntityRepository<PollingStation, Integer> {

    @Query(value = "SELECT * FROM find_nearby_polling_stations(:longitude, :latitude, :range)", nativeQuery = true)
    List<PollingStation> findNearbyPollingStations(@Param("latitude") Double latitude,
            @Param("longitude") Double longitude, @Param("range") Double range);
}
