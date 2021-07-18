package com.swehg.visitormanagement.controller;

import com.swehg.visitormanagement.dto.BuildingDTO;
import com.swehg.visitormanagement.dto.response.CommonResponseDTO;
import com.swehg.visitormanagement.service.BuildingService;
import com.swehg.visitormanagement.service.UserService;
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
@RequestMapping("v1/building")
public class BuildingController {

    private final BuildingService buildingService;
    private final UserService userService;

    @Autowired
    public BuildingController(BuildingService buildingService, UserService userService) {
        this.buildingService = buildingService;
        this.userService = userService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity addBuilding(@RequestBody BuildingDTO buildingDTO) {

        System.out.println("1111111111111111111");

        boolean result = buildingService.addBuilding(buildingDTO);
        return new ResponseEntity(new CommonResponseDTO( result, "Building saved successfully", null), HttpStatus.OK);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateBuilding(@RequestBody BuildingDTO buildingDTO) {
        boolean result = buildingService.updateBuilding(buildingDTO);
        return new ResponseEntity(new CommonResponseDTO( result, "Building updated successfully", null), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllBuilding() {
        List<BuildingDTO> allBuildings = buildingService.getAllBuildings();
        return new ResponseEntity(new CommonResponseDTO( true, "Building records found successfully", allBuildings), HttpStatus.OK);
    }

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActiveBuilding() {
        List<BuildingDTO> allBuildings = buildingService.getAllActiveBuildings();
        return new ResponseEntity(new CommonResponseDTO( true, "Active building records found successfully", allBuildings), HttpStatus.OK);
    }

    @GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendMail(@RequestParam("email") String email, @RequestParam("sub") String sub, @RequestParam("message") String message) {
        boolean b = userService.sendEmail(email, sub, message);
        return new ResponseEntity(new CommonResponseDTO( true, "Email sent successfully", null), HttpStatus.OK);
    }

}
