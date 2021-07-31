package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseForTableDTO;
import com.swehg.visitormanagement.enums.HistorySearchTypes;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface VisitService {
    boolean checkIn(CheckInRequestDTO dto);
    boolean checkOut(long visitId);
    Page<CommonVisitResponseForTableDTO> getAllNotCheckOut(String word, int index, int size);
    Page<CommonVisitResponseDTO> getAllOverdueCheckin(String word, int index, int size);
    Page<CommonVisitResponseDTO> getHistory(HistorySearchTypes type, String word, String startDate, String endDate, int index, int size);
}
