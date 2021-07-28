package com.swehg.visitormanagement.dto.response;

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
public class DashboardResponseDTO {
    private int checkin;
    private int overdue;
    private int visits;
    private int visitor;
}
