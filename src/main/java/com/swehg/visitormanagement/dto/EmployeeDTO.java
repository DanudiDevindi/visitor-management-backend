package com.swehg.visitormanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kavindu
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private long employeeId;
    private String firstName;
    private String lastName;
    private String nic;
    private String email;
    private String mobile;
    private String designation;
}
