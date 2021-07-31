package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.BuildingDTO;
import com.swehg.visitormanagement.dto.FloorDTO;
import com.swehg.visitormanagement.dto.request.FloorRequestDTO;
import com.swehg.visitormanagement.entity.BuildingEntity;
import com.swehg.visitormanagement.entity.FloorEntity;
import com.swehg.visitormanagement.enums.BuildingStatus;
import com.swehg.visitormanagement.enums.FloorStatus;
import com.swehg.visitormanagement.exception.FloorException;
import com.swehg.visitormanagement.exception.VisitorException;
import com.swehg.visitormanagement.repository.BuildingRepository;
import com.swehg.visitormanagement.repository.FloorRepository;
import com.swehg.visitormanagement.service.FloorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
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
        log.info("Execute addFloor: dto: " + dto);
        try {
            Optional<BuildingEntity> buildingById = buildingRepository.findById(dto.getBuildingId());
            if(!buildingById.isPresent()) throw new FloorException("Building not found");

            List<FloorEntity> allByBuildingEntity = floorRepository.findAllByBuildingEntity(buildingById.get());
            for (FloorEntity f : allByBuildingEntity) {
                if(f.getName().equals(dto.getName())) {
                    throw new FloorException("Another floor in this building exist with this floor name. Please try another name");
                }
            }

            floorRepository.save(new FloorEntity(dto.getName(), buildingById.get(), FloorStatus.ACTIVE));
            return true;
        } catch (Exception e) {
            log.error("Execute addFloor: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean updateFloor(FloorRequestDTO dto) {
        log.info("Execute updateFloor: dto: " + dto);
        try {
            Optional<FloorEntity> floorById = floorRepository.findById(dto.getFloorId());
            if(!floorById.isPresent()) throw new FloorException("Floor not found");
            Optional<BuildingEntity> buildingById = buildingRepository.findById(dto.getBuildingId());
            if(!buildingById.isPresent()) throw new FloorException("Building not found");

            FloorEntity floorEntity = floorById.get();

            List<FloorEntity> allByBuildingEntity = floorRepository.findAllByBuildingEntity(buildingById.get());
            for (FloorEntity f : allByBuildingEntity) {
                if(f.getName().equals(dto.getName())) {
                    if(f.getId()!=dto.getFloorId()) {
                        throw new FloorException("Another floor in this building exist with this floor name. Please try another name");
                    }
                }
            }

            floorEntity.setName(dto.getName());
            floorEntity.setBuildingEntity(buildingById.get());
            floorEntity.setFloorStatus(dto.getStatus());

            floorRepository.save(floorEntity);
            return true;
        } catch (Exception e) {
            log.error("Execute updateFloor: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean deleteFloor(long id) {
        log.info("Execute deleteFloor: id: " + id);
        try {
            Optional<FloorEntity> floorById = floorRepository.findById(id);
            if(!floorById.isPresent()) throw new FloorException("Floor not found");
            FloorEntity floorEntity = floorById.get();
            floorEntity.setFloorStatus(FloorStatus.DELETED);
            floorRepository.save(floorEntity);
            return true;
        } catch (Exception e) {
            log.error("Execute deleteFloor: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<FloorDTO> getAllFloor() {
        log.info("Execute getAllFloor:");
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
            log.error("Execute getAllFloor: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<FloorDTO> getActiveFloor() {
        log.info("Execute getActiveFloor:");
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
            log.error("Execute getActiveFloor: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<FloorDTO> getActiveFloorBuildingId(long id) {
        log.info("Execute getActiveFloorBuildingId: id: " + id);
        List<FloorDTO> activeFloorDTOList = new ArrayList<>();
        try {
            Optional<BuildingEntity> byId = buildingRepository.findById(id);
            if(!byId.isPresent()) throw new VisitorException("Building not found");
            List<FloorEntity> activeByBuildingEntityAndStatus = floorRepository.getActiveByBuildingEntityAndStatus(byId.get(), FloorStatus.ACTIVE);
            for (FloorEntity f : activeByBuildingEntityAndStatus) {
                activeFloorDTOList.add(new FloorDTO(f.getId(),
                        f.getName(),
                        new BuildingDTO(f.getBuildingEntity().getId(),
                                f.getBuildingEntity().getName(),
                                f.getBuildingEntity().getBuildingStatus()),
                        f.getFloorStatus()));
            }
            return activeFloorDTOList;
        } catch (Exception e) {
            log.error("Execute getActiveFloorBuildingId: " + e.getMessage());
            throw e;
        }
    }
}
