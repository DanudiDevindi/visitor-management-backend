package com.swehg.visitormanagement.dto.response;

import com.swehg.visitormanagement.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author kavindu
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonVisitResponseDTO {
    private long visitId;
    private VisitorDTO visitor;
    private Date checkInTime;
    private Date checkOutTime;
    private String purpose;
    private UserAllDetailDTO checkInUser;
    private UserAllDetailDTO checkOutUser;
    private FloorDTO floor;
    private PassCardDTO passCard;
    private EmployeeDTO employee;
}
