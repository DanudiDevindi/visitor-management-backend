package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.BuildingDTO;
import com.swehg.visitormanagement.entity.BuildingEntity;
import com.swehg.visitormanagement.enums.BuildingStatus;
import com.swehg.visitormanagement.enums.FloorStatus;
import com.swehg.visitormanagement.exception.BuildingException;
import com.swehg.visitormanagement.repository.BuildingRepository;
import com.swehg.visitormanagement.repository.FloorRepository;
import com.swehg.visitormanagement.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
        try {

            buildingRepository.save(new BuildingEntity(dto.getName(), BuildingStatus.ACTIVE));
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean deleteBuilding(long id) {
        try {

            Optional<BuildingEntity> buildingById = buildingRepository.findById(id);
            if(!buildingById.isPresent()) throw new BuildingException("Building not found");
            BuildingEntity buildingEntity = buildingById.get();
            buildingEntity.setBuildingStatus(BuildingStatus.DELETED);
            floorRepository.updateFloorStatusByBuilding(buildingEntity, FloorStatus.DELETED);
            buildingRepository.save(buildingEntity);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean updateBuilding(BuildingDTO dto) {
        try {

            Optional<BuildingEntity> buildingById = buildingRepository.findById(dto.getBuildingId());
            if(!buildingById.isPresent()) throw new BuildingException("Building not found");
            BuildingEntity buildingEntity = buildingById.get();
            buildingEntity.setName(dto.getName());
            buildingEntity.setBuildingStatus(dto.getStatus());

            if(dto.getStatus().equals(BuildingStatus.INACTIVE)) {
                floorRepository.updateFloorStatusByBuilding(buildingEntity, FloorStatus.INACTIVE);
            }

            buildingRepository.save(buildingEntity);

            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<BuildingDTO> getAllBuildings() {
        try {

            List<BuildingEntity> all = buildingRepository.getAllByExceptBuildingStatus(BuildingStatus.DELETED);
            List<BuildingDTO> buildingList = new ArrayList<>();
            for (BuildingEntity b : all) {
                buildingList.add(new BuildingDTO(b.getId(), b.getName(), b.getBuildingStatus()));
            }
            return buildingList;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<BuildingDTO> getAllActiveBuildings() {
        try {

            List<BuildingEntity> all = buildingRepository.findAllByBuildingStatus(BuildingStatus.ACTIVE);
            List<BuildingDTO> buildingList = new ArrayList<>();
            for (BuildingEntity b : all) {
                buildingList.add(new BuildingDTO(b.getId(), b.getName(), b.getBuildingStatus()));
            }
            return buildingList;

        } catch (Exception e) {
            throw e;
        }
    }
}
