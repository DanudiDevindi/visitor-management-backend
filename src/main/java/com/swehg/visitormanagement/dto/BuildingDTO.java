package com.swehg.visitormanagement.dto;

import com.swehg.visitormanagement.enums.BuildingStatus;
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
public class BuildingDTO {
    private long buildingId;
    private String name;
    private BuildingStatus status;
}
