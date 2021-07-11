package com.swehg.visitormanagement.controller;

import com.swehg.visitormanagement.dto.BuildingDTO;
import com.swehg.visitormanagement.dto.UserAllDetailDTO;
import com.swehg.visitormanagement.dto.response.CommonResponseDTO;
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
@RequestMapping("v1/receptionist")
public class ReceptionistController {

    private final UserService userService;

    @Autowired
    public ReceptionistController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewReceptionist(@RequestBody UserAllDetailDTO userAllDetailDTO) {
        boolean result = userService.addUser(userAllDetailDTO);
        return new ResponseEntity(new CommonResponseDTO( result, "Receptionist account created successfully", null), HttpStatus.OK);
    }

    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateReceptionist(@RequestBody UserAllDetailDTO userAllDetailDTO) {
        boolean result = userService.updateUser(userAllDetailDTO);
        return new ResponseEntity(new CommonResponseDTO( result, "Receptionist account updated successfully", null), HttpStatus.OK);
    }

    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateReceptionist(@RequestParam("word") String word) {
        List<UserAllDetailDTO> result = userService.getAllUser(word);
        return new ResponseEntity(new CommonResponseDTO( true, "Receptionist account records found successfully", result), HttpStatus.OK);
    }

}
