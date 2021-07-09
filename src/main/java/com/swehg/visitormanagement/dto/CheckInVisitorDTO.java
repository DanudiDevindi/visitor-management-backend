package com.swehg.visitormanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckInVisitorDTO {
    private long visitorId;
    private String visitorFirstName;
    private String visitorLastName;
    private String mobile;
    private String nic;
    private String email;
    private long passCardId;
}
