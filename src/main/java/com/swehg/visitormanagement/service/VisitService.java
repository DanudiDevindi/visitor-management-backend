package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import com.swehg.visitormanagement.enums.HistorySearchTypes;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface VisitService {
    boolean checkIn(CheckInRequestDTO dto);
    boolean checkOut(long visitId);
    Page<CommonVisitResponseDTO> getAllNotCheckOut(String word, int index, int size);
    Page<CommonVisitResponseDTO> getAllOverdueCheckin(String word, int index, int size);
    Page<CommonVisitResponseDTO> getHistory(HistorySearchTypes type, String word, Date startDate, Date endDate, int index, int size);
}
