package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.BuildingEntity;
import com.swehg.visitormanagement.entity.FloorEntity;
import com.swehg.visitormanagement.enums.FloorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FloorRepository extends JpaRepository<FloorEntity, Long> {
    @Query("SELECT f FROM FloorEntity f WHERE f.floorStatus<>:floorStatus")
    List<FloorEntity> getAllFloorsExceptStatus(@Param("floorStatus")FloorStatus floorStatus);

    List<FloorEntity> findAllByFloorStatus(FloorStatus floorStatus);

    @Query("UPDATE FloorEntity f SET f.floorStatus=:floorStatus WHERE f.buildingEntity=:building")
    int updateFloorStatusByBuilding(@Param("building") BuildingEntity buildingEntity, @Param("floorStatus") FloorStatus floorStatus);
}
