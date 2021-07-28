package com.swehg.visitormanagement.dto.response;

import com.swehg.visitormanagement.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author hp
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonVisitResponseForTableDTO {
    private long visitId;
    private String passCard;
    private Date checkInTime;
    private String nic;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String employee;
}
