package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;

public interface VisitService {
    boolean checkIn(CheckInRequestDTO dto);
}
