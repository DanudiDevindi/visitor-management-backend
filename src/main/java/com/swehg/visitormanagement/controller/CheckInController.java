package com.swehg.visitormanagement.controller;

import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonResponseDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import com.swehg.visitormanagement.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @PatchMapping(value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkOut(@RequestParam("id") long visitId) {
        visitService.checkOut(visitId);
        return new ResponseEntity(new CommonResponseDTO(true, "Visitor checked out successfully", null), HttpStatus.OK);
    }

    @GetMapping(value = "/checked", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity checkOut(@RequestParam("word") String word, @RequestParam("index") int index, @RequestParam("size") int size) {
        Page<CommonVisitResponseDTO> allNotCheckOut = visitService.getAllNotCheckOut(word, index, size);
        return new ResponseEntity(new CommonResponseDTO(true, "Checked in records found successfully", allNotCheckOut), HttpStatus.OK);
    }

    @GetMapping(value = "/overdue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOverdueCheckin(@RequestParam("word") String word, @RequestParam("index") int index, @RequestParam("size") int size) {
        Page<CommonVisitResponseDTO> allNotCheckOut = visitService.getAllOverdueCheckin(word, index, size);
        return new ResponseEntity(new CommonResponseDTO(true, "Overdue checkin records found successfully", allNotCheckOut), HttpStatus.OK);
    }

}
