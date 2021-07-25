package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    boolean addEmployee(EmployeeDTO dto);
    boolean updateEmployee(EmployeeDTO dto);
    Page<EmployeeDTO> getAllEmployee(String word, int index, int size);
    List<String> getEmployee();
}
