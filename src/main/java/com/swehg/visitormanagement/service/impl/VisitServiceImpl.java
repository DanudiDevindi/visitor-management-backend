package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.CheckInVisitorDTO;
import com.swehg.visitormanagement.dto.JwtTokenDTO;
import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;
import com.swehg.visitormanagement.entity.*;
import com.swehg.visitormanagement.enums.PassCardStatus;
import com.swehg.visitormanagement.exception.VisitException;
import com.swehg.visitormanagement.repository.*;
import com.swehg.visitormanagement.service.VisitService;
import com.swehg.visitormanagement.util.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {

    private final EmployeeRepository employeeRepository;
    private final VisitorRepository visitorRepository;
    private final PassCardRepository passCardRepository;
    private final TokenValidator tokenValidator;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final FloorRepository floorRepository;

    @Autowired
    public VisitServiceImpl(EmployeeRepository employeeRepository, VisitorRepository visitorRepository, PassCardRepository passCardRepository, TokenValidator tokenValidator, UserRepository userRepository, VisitRepository visitRepository, FloorRepository floorRepository) {
        this.employeeRepository = employeeRepository;
        this.visitorRepository = visitorRepository;
        this.passCardRepository = passCardRepository;
        this.tokenValidator = tokenValidator;
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
        this.floorRepository = floorRepository;
    }

    /**
     * checkin service method
     *
     * @param dto
     * @return true or false
     */
    @Override
    public boolean checkIn(CheckInRequestDTO dto) {
        try {

            JwtTokenDTO jwtTokenDTO = tokenValidator.retrieveCurrentUserInformationFromToken();
            long user_id = jwtTokenDTO.getUser_id();

            Optional<UserEntity> userById = userRepository.findById(user_id);
            if(!userById.isPresent()) throw new VisitException("User not found");

            Optional<EmployeeEntity> employeeById = employeeRepository.findById(dto.getEmployeeId());
            if(!employeeById.isPresent()) throw new VisitException("Staff member not found");

            Optional<FloorEntity> floorById = floorRepository.findById(dto.getFloorId());
            if(!floorById.isPresent()) throw new VisitException("Floor not found");

            VisitorEntity visitorEntity = null;

            for (CheckInVisitorDTO c : dto.getVisitors()) {

                if(c.getVisitorId()!=0) {
                    Optional<VisitorEntity> visitorById = visitorRepository.findById(c.getVisitorId());
                    if(!employeeById.isPresent()) throw new VisitException("Visitor not found");
                    visitorEntity = visitorById.get();
                } else {
                    visitorEntity = visitorRepository.save(new VisitorEntity(c.getVisitorFirstName(), c.getVisitorLastName(), c.getMobile(), c.getNic(), c.getEmail(), new Date()));
                }

                Optional<PassCardEntity> passCardById = passCardRepository.findById(c.getPassCardId());
                if(!passCardById.isPresent()) throw new VisitException("Pass card not found");
                PassCardEntity passCardEntity = passCardById.get();
                if(passCardEntity.getStatus().equals(PassCardStatus.RESERVED)) throw new VisitException("Pass card" + passCardEntity.getName() + " already reserved");
                passCardEntity.setStatus(PassCardStatus.RESERVED);
                PassCardEntity savedPassCard = passCardRepository.save(passCardEntity);
                visitRepository.save(new VisitEntity(visitorEntity, new Date(), null, dto.getPurpose(), userById.get(), null, floorById.get(), savedPassCard, employeeById.get()));
            }

            return true;

        } catch (Exception e) {
            throw e;
        }
    }

}
