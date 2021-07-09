package com.swehg.visitormanagement.dto;

import com.swehg.visitormanagement.enums.UserRoles;
import com.swehg.visitormanagement.enums.UserStatus;
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
public class UserAllDetailDTO {
    private long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String nic;
    private String email;
    private String mobile;
    private String password;
    private Date createdDate;
    private UserRoles role;
    private UserStatus status;
}
