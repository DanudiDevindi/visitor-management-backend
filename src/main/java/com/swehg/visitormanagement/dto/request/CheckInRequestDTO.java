package com.swehg.visitormanagement.dto.request;

import com.swehg.visitormanagement.dto.CheckInVisitorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRequestDTO {
    private List<CheckInVisitorDTO> visitors;
    private String purpose;
    private long employeeId;
    private long floorId;
}
