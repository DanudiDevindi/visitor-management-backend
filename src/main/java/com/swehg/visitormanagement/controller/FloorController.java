package com.swehg.visitormanagement.controller;

import com.swehg.visitormanagement.dto.FloorDTO;
import com.swehg.visitormanagement.dto.VisitorDTO;
import com.swehg.visitormanagement.dto.request.FloorRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonResponseDTO;
import com.swehg.visitormanagement.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hp
 */

@RestController
@CrossOrigin
@RequestMapping("v1/floor")
public class FloorController {

    private final FloorService floorService;

    @Autowired
    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewFloor(@RequestBody FloorRequestDTO floorRequestDTO) {
        boolean result = floorService.addFloor(floorRequestDTO);
        return new ResponseEntity(new CommonResponseDTO(result, "Floor saved successfully", null), HttpStatus.OK);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateFloor(@RequestBody FloorRequestDTO floorRequestDTO) {
        boolean result = floorService.updateFloor(floorRequestDTO);
        return new ResponseEntity(new CommonResponseDTO(result, "Floor updated successfully", null), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllFloor() {
        List<FloorDTO> allFloor = floorService.getAllFloor();
        return new ResponseEntity(new CommonResponseDTO(true, "Floor records found successfully", allFloor), HttpStatus.OK);
    }

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActiveFloor() {
        List<FloorDTO> allFloor = floorService.getActiveFloor();
        return new ResponseEntity(new CommonResponseDTO(true, "Active floor records found successfully", allFloor), HttpStatus.OK);
    }

    @GetMapping(value = "/active/building/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActiveFloor(@PathVariable long id) {
        List<FloorDTO> allFloor = floorService.getActiveFloorBuildingId(id);
        return new ResponseEntity(new CommonResponseDTO(true, "Active floor records found successfully", allFloor), HttpStatus.OK);
    }

    @PatchMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteFloor(@RequestParam("id")long id) {
        boolean result = floorService.deleteFloor(id);
        return new ResponseEntity(new CommonResponseDTO(result, "Floor deleted successfully", null), HttpStatus.OK);
    }

}
