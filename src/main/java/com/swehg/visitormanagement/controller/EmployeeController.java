package com.swehg.visitormanagement.controller;

import com.swehg.visitormanagement.dto.EmployeeDTO;
import com.swehg.visitormanagement.dto.response.CommonResponseDTO;
import com.swehg.visitormanagement.dto.response.EmployeeSearchableResponseDTO;
import com.swehg.visitormanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("v1/employee")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        boolean result = employeeService.addEmployee(employeeDTO);
        return new ResponseEntity(new CommonResponseDTO(result, "Employee saved successfully", null), HttpStatus.OK);
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        boolean result = employeeService.updateEmployee(employeeDTO);
        return new ResponseEntity(new CommonResponseDTO(result, "Employee updated successfully", null), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllEmployee(@RequestParam("word") String word, @RequestParam("index") int index, @RequestParam("size") int size) {
        Page<EmployeeDTO> result = employeeService.getAllEmployee(word, index, size);
        return new ResponseEntity(new CommonResponseDTO(true, "Employee records found successfully", result), HttpStatus.OK);
    }

    @GetMapping(value = "/search/all/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllActiveEmployeeForSearch() {
        List<EmployeeSearchableResponseDTO> result = employeeService.getEmployee();
        return new ResponseEntity(new CommonResponseDTO(true, "Employee records found successfully", result), HttpStatus.OK);
    }


}
