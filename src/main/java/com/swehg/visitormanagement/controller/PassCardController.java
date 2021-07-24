package com.swehg.visitormanagement.controller;

import com.swehg.visitormanagement.dto.FloorDTO;
import com.swehg.visitormanagement.dto.PassCardDTO;
import com.swehg.visitormanagement.dto.request.FloorRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonResponseDTO;
import com.swehg.visitormanagement.service.PassCardService;
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
@RequestMapping("v1/pass")
public class PassCardController {

    private final PassCardService passCardService;

    @Autowired
    public PassCardController(PassCardService passCardService) {
        this.passCardService = passCardService;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewCard(@RequestBody PassCardDTO passCardDTO) {
        boolean b = passCardService.addPassCard(passCardDTO);
        return new ResponseEntity(new CommonResponseDTO(b, "Pass card saved successfully", null), HttpStatus.OK);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCard(@RequestBody PassCardDTO passCardDTO) {
        boolean b = passCardService.updatePassCard(passCardDTO);
        return new ResponseEntity(new CommonResponseDTO(b, "Pass card updated successfully", null), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllCard() {
        List<PassCardDTO> allPassCard = passCardService.getAllPassCard();
        return new ResponseEntity(new CommonResponseDTO(true, "Pass card records found successfully", allPassCard), HttpStatus.OK);
    }

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActiveCard() {
        List<PassCardDTO> activePassCard = passCardService.getActivePassCard();
        return new ResponseEntity(new CommonResponseDTO(true, "Active Pass card records found successfully", activePassCard), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActiveCard(@RequestParam("id") long id) {
        boolean b = passCardService.deletePassCard(id);
        return new ResponseEntity(new CommonResponseDTO(true, "Pass card deleted successfully", null), HttpStatus.OK);
    }
}
