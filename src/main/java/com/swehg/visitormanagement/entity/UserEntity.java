package com.swehg.visitormanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swehg.visitormanagement.enums.UserRoles;
import com.swehg.visitormanagement.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String nic;
    @Column
    private String email;
    @Column
    private String mobile;
    @Column
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Enumerated(EnumType.STRING)
    private UserRoles role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
