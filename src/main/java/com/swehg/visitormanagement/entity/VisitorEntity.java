package com.swehg.visitormanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visitor")
public class VisitorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String mobile;
    @Column
    private String nic;
    @Column
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public VisitorEntity(String firstName, String lastName, String mobile, String nic, String email, Date createdDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.nic = nic;
        this.email = email;
        this.createdDate = createdDate;
    }
}
