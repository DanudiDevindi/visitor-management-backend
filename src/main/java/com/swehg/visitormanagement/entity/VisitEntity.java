package com.swehg.visitormanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visit")
public class VisitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private VisitorEntity visitorEntity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkinTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkoutTime;
    private String purpose;
    @ManyToOne
    @JoinColumn(name = "check_in_resep_id")
    private UserEntity checkInUserEntity;
    @ManyToOne
    @JoinColumn(name = "check_out_resep_id")
    private UserEntity checkOutUserEntity;
    @ManyToOne
    @JoinColumn(name = "floor_id")
    private FloorEntity floorEntity;
    @ManyToOne
    @JoinColumn(name = "pass_card_id")
    private PassCardEntity passCardEntity;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employeeEntity;

    public VisitEntity(VisitorEntity visitorEntity, Date checkingTime, Date checkoutTime, String purpose, UserEntity checkInUserEntity, UserEntity checkOutUserEntity, FloorEntity floorEntity, PassCardEntity passCardEntity, EmployeeEntity employeeEntity) {
        this.visitorEntity = visitorEntity;
        this.checkinTime = checkingTime;
        this.checkoutTime = checkoutTime;
        this.purpose = purpose;
        this.checkInUserEntity = checkInUserEntity;
        this.checkOutUserEntity = checkOutUserEntity;
        this.floorEntity = floorEntity;
        this.passCardEntity = passCardEntity;
        this.employeeEntity = employeeEntity;
    }
}