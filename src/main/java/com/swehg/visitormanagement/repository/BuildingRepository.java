package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.BuildingEntity;
import com.swehg.visitormanagement.enums.BuildingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author hp
 */

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {
    @Query("SELECT b FROM BuildingEntity b WHERE b.buildingStatus<>:buildingStatus")
    List<BuildingEntity> getAllByExceptBuildingStatus(@Param("buildingStatus") BuildingStatus buildingStatus);
    List<BuildingEntity> findAllByBuildingStatus(BuildingStatus buildingStatus);
    Optional<BuildingEntity> findByName(String name);
}
