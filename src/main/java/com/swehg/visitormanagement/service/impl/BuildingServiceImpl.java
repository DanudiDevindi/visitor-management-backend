package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.BuildingDTO;
import com.swehg.visitormanagement.entity.BuildingEntity;
import com.swehg.visitormanagement.enums.BuildingStatus;
import com.swehg.visitormanagement.enums.FloorStatus;
import com.swehg.visitormanagement.exception.BuildingException;
import com.swehg.visitormanagement.repository.BuildingRepository;
import com.swehg.visitormanagement.repository.FloorRepository;
import com.swehg.visitormanagement.service.BuildingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final FloorRepository floorRepository;

    @Autowired
    public BuildingServiceImpl(BuildingRepository buildingRepository, FloorRepository floorRepository) {
        this.buildingRepository = buildingRepository;
        this.floorRepository = floorRepository;
    }

    @Override
    public boolean addBuilding(BuildingDTO dto) {
        log.info("Execute addBuilding: dto: " + dto);
        try {

            buildingRepository.save(new BuildingEntity(dto.getName(), BuildingStatus.ACTIVE));
            return true;

        } catch (Exception e) {
            log.error("Execute addBuilding: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteBuilding(long id) {
        log.info("Execute deleteBuilding: id: " + id);
        try {

            Optional<BuildingEntity> buildingById = buildingRepository.findById(id);
            if(!buildingById.isPresent()) throw new BuildingException("Building not found");
            BuildingEntity buildingEntity = buildingById.get();
            floorRepository.updateFloorStatusByBuilding(FloorStatus.DELETED, buildingEntity.getId());

            BuildingStatus deleted = BuildingStatus.DELETED;

            System.out.println("XXXXXXXXXXXXXXXXXXX: " + deleted);

            buildingEntity.setBuildingStatus(deleted);
            buildingRepository.save(buildingEntity);
            return true;

        } catch (Exception e) {
            log.error("Execute deleteBuilding: " + e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateBuilding(BuildingDTO dto) {
        log.info("Execute updateBuilding: dto: " + dto);
        try {

            Optional<BuildingEntity> buildingById = buildingRepository.findById(dto.getBuildingId());
            if(!buildingById.isPresent()) throw new BuildingException("Building not found");
            BuildingEntity buildingEntity = buildingById.get();
            buildingEntity.setName(dto.getName());
            buildingEntity.setBuildingStatus(dto.getStatus());

            if(dto.getStatus().equals(BuildingStatus.INACTIVE)) {
                floorRepository.updateFloorStatusByBuilding(FloorStatus.INACTIVE, buildingEntity.getId());
            }

            buildingRepository.save(buildingEntity);

            return true;

        } catch (Exception e) {
            log.error("Execute updateBuilding: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BuildingDTO> getAllBuildings() {
        log.info("Execute getAllBuildings:");
        try {

            List<BuildingEntity> all = buildingRepository.getAllByExceptBuildingStatus(BuildingStatus.DELETED);
            List<BuildingDTO> buildingList = new ArrayList<>();
            for (BuildingEntity b : all) {
                buildingList.add(new BuildingDTO(b.getId(), b.getName(), b.getBuildingStatus()));
            }
            return buildingList;

        } catch (Exception e) {
            log.error("Execute getAllBuildings: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BuildingDTO> getAllActiveBuildings() {
        log.info("Execute getAllBuildings:");
        try {

            List<BuildingEntity> all = buildingRepository.findAllByBuildingStatus(BuildingStatus.ACTIVE);
            List<BuildingDTO> buildingList = new ArrayList<>();
            for (BuildingEntity b : all) {
                buildingList.add(new BuildingDTO(b.getId(), b.getName(), b.getBuildingStatus()));
            }
            return buildingList;

        } catch (Exception e) {
            log.error("Execute getAllBuildings: " + e.getMessage());
            throw e;
        }
    }
}
