package com.swehg.visitormanagement.controller;

import com.swehg.visitormanagement.dto.VisitorDTO;
import com.swehg.visitormanagement.dto.response.CommonResponseDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import com.swehg.visitormanagement.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hp
 */

@RestController
@CrossOrigin
@RequestMapping("v1/visitor")
public class VisitorController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping(value = "/filter/nic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity filterVisitorByNic(@RequestParam("nic") String nic) {
        VisitorDTO visitorDTO = visitorService.filterVisitorByNic(nic);
        return new ResponseEntity(new CommonResponseDTO((visitorDTO!=null), (visitorDTO!=null)?"Visitor found successfully":"Visitor not found", visitorDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllVisitor(@Param("word") String word, @RequestParam("index") int index, @RequestParam("size") int size) {
        Page<VisitorDTO> visitorDTOS = visitorService.filterVisitor(word, index, size);
        return new ResponseEntity(new CommonResponseDTO(true, "Matching visitor details filter successfully", visitorDTOS), HttpStatus.OK);
    }

}
