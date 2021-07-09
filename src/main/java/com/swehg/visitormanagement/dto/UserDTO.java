package com.swehg.visitormanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swehg.visitormanagement.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private Date createdDate;
    private UserRoles role;
}
