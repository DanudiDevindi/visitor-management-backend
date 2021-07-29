package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.*;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import com.swehg.visitormanagement.dto.response.EmployeeSearchableResponseDTO;
import com.swehg.visitormanagement.entity.EmployeeEntity;
import com.swehg.visitormanagement.entity.VisitEntity;
import com.swehg.visitormanagement.enums.EmployeeStatus;
import com.swehg.visitormanagement.exception.EmployeeException;
import com.swehg.visitormanagement.repository.EmployeeRepository;
import com.swehg.visitormanagement.service.EmployeeService;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) {
        log.info("Execute addEmployee: dto: " + dto);
        try {
            Optional<EmployeeEntity> byNic = employeeRepository.findByNic(dto.getNic());
            Optional<EmployeeEntity> byMobile = employeeRepository.findByMobile(dto.getMobile());
            Optional<EmployeeEntity> byEmail = employeeRepository.findByEmail(dto.getEmail());

            System.out.println(byNic.isPresent());

            if(byNic.isPresent()) throw new EmployeeException("Already exist an employee with this NIC");
            if(byMobile.isPresent()) throw new EmployeeException("Already exist an employee with this Mobile Number");
            if(byEmail.isPresent()) throw new EmployeeException("Already exist an employee with this Email");

            employeeRepository.save(new EmployeeEntity(
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getNic(),
                    dto.getMobile(),
                    dto.getEmail(),
                    dto.getDesignation(),
                    EmployeeStatus.ACTIVE
            ));

            return true;

        } catch (Exception e) {
            log.error("Execute addEmployee: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) {
        log.info("Execute updateEmployee: dto: " + dto);
        try {
        Optional<EmployeeEntity> byId = employeeRepository.findById(dto.getEmployeeId());
        if(!byId.isPresent()) throw new EmployeeException("Employee not found");

        EmployeeEntity employeeEntity = byId.get();

        if(!employeeEntity.getNic().equals(dto.getNic())) {
            Optional<EmployeeEntity> byNic = employeeRepository.findByNic(dto.getNic());
            if(byNic.isPresent()) throw new EmployeeException("Already exist an employee with this NIC");
        }

        if(!employeeEntity.getMobile().equals(dto.getMobile())) {
            Optional<EmployeeEntity> byMobile = employeeRepository.findByMobile(dto.getMobile());
            if(byMobile.isPresent()) throw new EmployeeException("Already exist an employee with this Mobile Number");
        }

        if(!employeeEntity.getEmail().equals(dto.getEmail())) {
            Optional<EmployeeEntity> byEmail = employeeRepository.findByEmail(dto.getEmail());
            if (byEmail.isPresent()) throw new EmployeeException("Already exist an employee with this Email");
        }

        employeeEntity.setFirstName(dto.getFirstName());
        employeeEntity.setLastName(dto.getLastName());
        employeeEntity.setNic(dto.getNic());
        employeeEntity.setMobile(dto.getMobile());
        employeeEntity.setEmail(dto.getEmail());
        employeeEntity.setDesignation(dto.getDesignation());
        employeeEntity.setStatus(EmployeeStatus.ACTIVE);

        employeeRepository.save(employeeEntity);
        return true;
        } catch (Exception e) {
            log.error("Execute updateEmployee: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<EmployeeDTO> getAllEmployee(String word, int index, int size) {
        log.info("Execute getAllEmployee: word: " + word + ", index: " + index + ", size: " + size);
        try {
            Pageable pageable = PageRequest.of(index, size);
            Page<EmployeeEntity> allEmployee = employeeRepository.getAllEmployee(word, pageable);
            return allEmployee.map(this::mapEmployeeEntityToEmployeeDTO);
        } catch (Exception e) {
            log.error("Execute getAllEmployee: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<EmployeeSearchableResponseDTO> getEmployee() {
        List<EmployeeSearchableResponseDTO> searchableEmployeeList = new ArrayList<>();
        log.info("Execute getEmployee:");
        try {
            List<EmployeeEntity> allEmployee = employeeRepository.getAllEmployeeForSearch(EmployeeStatus.ACTIVE);
            for (EmployeeEntity e : allEmployee) {
                EmployeeSearchableResponseDTO dto = new EmployeeSearchableResponseDTO(e.getId(), e.getFirstName(), e.getLastName(), e.getNic(), e.getEmail(), e.getMobile(), e.getDesignation(), e.getFirstName() + " " + e.getLastName() + " - " + e.getMobile());
                searchableEmployeeList.add(dto);
            }
            return searchableEmployeeList;
        } catch (Exception e) {
            log.error("Execute getEmployee: " + e.getMessage());
            throw e;
        }
    }

    private EmployeeDTO mapEmployeeEntityToEmployeeDTO(EmployeeEntity e) {
        return new EmployeeDTO(e.getId(),
                e.getFirstName(),
                e.getLastName(),
                e.getNic(),
                e.getEmail(),
                e.getMobile(),
                e.getDesignation());
    }
}
