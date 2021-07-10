package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.BuildingEntity;
import com.swehg.visitormanagement.enums.BuildingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hp
 */

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {
    List<BuildingEntity> findAllByBuildingStatus(BuildingStatus buildingStatus);
}
