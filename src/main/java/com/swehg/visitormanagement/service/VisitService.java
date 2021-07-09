package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import org.springframework.data.domain.Page;

public interface VisitService {
    boolean checkIn(CheckInRequestDTO dto);
    boolean checkOut(long visitId);
    Page<CommonVisitResponseDTO> getAllNotCheckOut(int index, int size);
}
