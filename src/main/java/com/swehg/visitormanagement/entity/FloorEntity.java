package com.swehg.visitormanagement.entity;

import com.swehg.visitormanagement.enums.FloorStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "floor")
public class FloorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "building_id")
    private BuildingEntity buildingEntity;
    @Enumerated(EnumType.STRING)
    private FloorStatus floorStatus;

    public FloorEntity(String name, BuildingEntity buildingEntity, FloorStatus floorStatus) {
        this.name = name;
        this.buildingEntity = buildingEntity;
        this.floorStatus = floorStatus;
    }
}
