package com.swehg.visitormanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hp
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchableResponseDTO {
    private long employeeId;
    private String firstName;
    private String lastName;
    private String nic;
    private String email;
    private String mobile;
    private String designation;
    private String search;

    public String customToString() {
        return "{" +
                "employeeId:" + employeeId +
                ", firstName:'" + firstName + '\'' +
                ", lastName:'" + lastName + '\'' +
                ", nic:'" + nic + '\'' +
                ", email:'" + email + '\'' +
                ", mobile:'" + mobile + '\'' +
                ", designation:'" + designation + '\'' +
                ", search:'" + search + '\'' +
                '}';
    }
}
