package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import org.springframework.data.domain.Page;

public interface VisitService {
    boolean checkIn(CheckInRequestDTO dto);
    boolean checkOut(long visitId);
    Page<CommonVisitResponseDTO> getAllNotCheckOut(String word, int index, int size);
    Page<CommonVisitResponseDTO> getAllOverdueCheckin(String word, int index, int size);
}
