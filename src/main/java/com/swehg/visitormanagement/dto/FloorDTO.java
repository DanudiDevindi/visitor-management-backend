package com.swehg.visitormanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kavindu
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FloorDTO {
    private long floorId;
    private String name;
    private BuildingDTO building;
}
