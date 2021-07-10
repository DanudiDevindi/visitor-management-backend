package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.BuildingDTO;
import com.swehg.visitormanagement.dto.FloorDTO;
import com.swehg.visitormanagement.dto.request.FloorRequestDTO;
import com.swehg.visitormanagement.entity.BuildingEntity;
import com.swehg.visitormanagement.entity.FloorEntity;
import com.swehg.visitormanagement.enums.BuildingStatus;
import com.swehg.visitormanagement.enums.FloorStatus;
import com.swehg.visitormanagement.exception.FloorException;
import com.swehg.visitormanagement.repository.BuildingRepository;
import com.swehg.visitormanagement.repository.FloorRepository;
import com.swehg.visitormanagement.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;

    @Autowired
    public FloorServiceImpl(FloorRepository floorRepository, BuildingRepository buildingRepository) {
        this.floorRepository = floorRepository;
        this.buildingRepository = buildingRepository;
    }

    @Override
    public boolean addFloor(FloorRequestDTO dto) {
        try {
            Optional<BuildingEntity> buildingById = buildingRepository.findById(dto.getBuildingId());
            if(!buildingById.isPresent()) throw new FloorException("Building not found");
            floorRepository.save(new FloorEntity(dto.getName(), buildingById.get(), FloorStatus.ACTIVE));
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean updateFloor(FloorRequestDTO dto) {
        try {
            Optional<FloorEntity> floorById = floorRepository.findById(dto.getFloorId());
            if(!floorById.isPresent()) throw new FloorException("Floor not found");
            Optional<BuildingEntity> buildingById = buildingRepository.findById(dto.getBuildingId());
            if(!buildingById.isPresent()) throw new FloorException("Building not found");
            floorRepository.save(new FloorEntity(dto.getName(), buildingById.get(), dto.getStatus()));
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<FloorDTO> getAllFloor() {
        try {
            List<FloorEntity> allFloorsExceptStatus = floorRepository.getAllFloorsExceptStatus(FloorStatus.DELETED);
            List<FloorDTO> floorDTOList = new ArrayList<>();
            for (FloorEntity f : allFloorsExceptStatus) {
                floorDTOList.add(new FloorDTO(f.getId(),
                        f.getName(),
                        new BuildingDTO(f.getBuildingEntity().getId(),
                                f.getBuildingEntity().getName(),
                                f.getBuildingEntity().getBuildingStatus()),
                        f.getFloorStatus()));
            }
            return floorDTOList;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<FloorDTO> getActiveFloor() {
        try {
            List<FloorEntity> allByFloorStatus = floorRepository.findAllByFloorStatus(FloorStatus.ACTIVE);
            List<FloorDTO> activeFloorDTOList = new ArrayList<>();
            for (FloorEntity f : allByFloorStatus) {
                activeFloorDTOList.add(new FloorDTO(f.getId(),
                        f.getName(),
                        new BuildingDTO(f.getBuildingEntity().getId(),
                                f.getBuildingEntity().getName(),
                                f.getBuildingEntity().getBuildingStatus()),
                        f.getFloorStatus()));
            }
            return activeFloorDTOList;
        } catch (Exception e) {
            throw e;
        }
    }
}
