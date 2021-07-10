package com.swehg.visitormanagement.dto.request;

import com.swehg.visitormanagement.dto.BuildingDTO;
import com.swehg.visitormanagement.enums.FloorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hp
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FloorRequestDTO {
    private long floorId;
    private String name;
    private FloorStatus status;
    private long buildingId;
}
