package com.swehg.visitormanagement.controller;

import com.swehg.visitormanagement.dto.VisitorDTO;
import com.swehg.visitormanagement.dto.response.CommonResponseDTO;
import com.swehg.visitormanagement.dto.response.DashboardResponseDTO;
import com.swehg.visitormanagement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hp
 */

@RestController
@CrossOrigin
@RequestMapping("v1/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDashboardData() {
        DashboardResponseDTO dashboardData = dashboardService.getDashboardData();
        return new ResponseEntity(new CommonResponseDTO(true, "Dashboard data found successfully", dashboardData), HttpStatus.OK);
    }

}
