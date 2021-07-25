package com.swehg.visitormanagement.entity;

import com.swehg.visitormanagement.enums.EmployeeStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String nic;
    @Column
    private String mobile;
    @Column
    private String email;
    @Column
    private String designation;
    @Column
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    public EmployeeEntity(String firstName, String lastName, String nic, String mobile, String email, String designation, EmployeeStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.mobile = mobile;
        this.email = email;
        this.designation = designation;
        this.status = status;
    }
}
