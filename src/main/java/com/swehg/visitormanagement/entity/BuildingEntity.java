package com.swehg.visitormanagement.entity;

import com.swehg.visitormanagement.enums.BuildingStatus;
import com.swehg.visitormanagement.enums.FloorStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "building")
public class BuildingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    private BuildingStatus buildingStatus;
}
