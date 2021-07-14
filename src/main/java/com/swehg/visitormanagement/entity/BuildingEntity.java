package com.swehg.visitormanagement.entity;

import com.swehg.visitormanagement.enums.BuildingStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private BuildingStatus buildingStatus;
    @OneToMany(mappedBy = "buildingEntity")
    private List<FloorEntity> floorEntityList;

    public BuildingEntity(String name, BuildingStatus buildingStatus) {
        this.name = name;
        this.buildingStatus = buildingStatus;
    }
}
