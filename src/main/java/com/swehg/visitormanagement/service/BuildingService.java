package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.BuildingDTO;

import java.util.List;

public interface BuildingService {
    boolean addBuilding(BuildingDTO dto);
    boolean updateBuilding(BuildingDTO dto);
    boolean deleteBuilding(long id);
    List<BuildingDTO> getAllBuildings();
    List<BuildingDTO> getAllActiveBuildings();
}
