package com.swehg.visitormanagement.controller;

import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonResponseDTO;
import com.swehg.visitormanagement.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * this controller responsible for visitor checkin process
 */

@RestController
@CrossOrigin
@RequestMapping("v1/visit")
public class CheckInController {

    private final VisitService visitService;

    @Autowired
    public CheckInController(VisitService visitService) {
        this.visitService = visitService;
    }

    /**
     * visitor checkin endpoint
     *
     * @param checkInRequestDTO
     * @return ResponseEntity
     */
    @PostMapping(value = "/checkin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkIn(@RequestBody CheckInRequestDTO checkInRequestDTO) {
        visitService.checkIn(checkInRequestDTO);
        return new ResponseEntity(new CommonResponseDTO(true, "Visitor(s) checked in successfully", null), HttpStatus.OK);
    }

}
