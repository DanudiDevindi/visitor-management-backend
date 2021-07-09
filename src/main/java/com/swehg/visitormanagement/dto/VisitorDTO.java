package com.swehg.visitormanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author kavindu
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDTO {
    private long visitorId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String nic;
    private String email;
    private Date createDate;
}
