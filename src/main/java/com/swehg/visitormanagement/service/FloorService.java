package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.FloorDTO;
import com.swehg.visitormanagement.dto.request.FloorRequestDTO;

import java.util.List;

public interface FloorService {
    boolean addFloor(FloorRequestDTO dto);
    boolean updateFloor(FloorRequestDTO dto);
    List<FloorDTO> getAllFloor();
    List<FloorDTO> getActiveFloor();
}
