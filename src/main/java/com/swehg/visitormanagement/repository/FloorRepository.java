package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.BuildingEntity;
import com.swehg.visitormanagement.entity.FloorEntity;
import com.swehg.visitormanagement.enums.FloorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FloorRepository extends JpaRepository<FloorEntity, Long> {
    @Query("SELECT f FROM FloorEntity f WHERE f.floorStatus<>:floorStatus")
    List<FloorEntity> getAllFloorsExceptStatus(@Param("floorStatus")FloorStatus floorStatus);

    List<FloorEntity> findAllByFloorStatus(FloorStatus floorStatus);

    @Modifying
    @Query(value = "UPDATE floor f SET f.floor_status=?1 WHERE f.building_id=?2", nativeQuery = true)
    int updateFloorStatusByBuilding(String floorStatus, long id);

    @Query("SELECT f FROM FloorEntity f WHERE f.buildingEntity=:building AND f.floorStatus =:floorStatus")
    List<FloorEntity> getActiveByBuildingEntityAndStatus(@Param("building")BuildingEntity buildingEntity, @Param("floorStatus") FloorStatus floorStatus);

}
