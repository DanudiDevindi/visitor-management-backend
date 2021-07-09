package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.*;
import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import com.swehg.visitormanagement.entity.*;
import com.swehg.visitormanagement.enums.PassCardStatus;
import com.swehg.visitormanagement.exception.VisitException;
import com.swehg.visitormanagement.repository.*;
import com.swehg.visitormanagement.service.VisitService;
import com.swehg.visitormanagement.util.DateGenerator;
import com.swehg.visitormanagement.util.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final DateGenerator dateGenerator;

    @Autowired
    public VisitServiceImpl(EmployeeRepository employeeRepository, VisitorRepository visitorRepository, PassCardRepository passCardRepository, TokenValidator tokenValidator, UserRepository userRepository, VisitRepository visitRepository, FloorRepository floorRepository, DateGenerator dateGenerator) {
        this.employeeRepository = employeeRepository;
        this.visitorRepository = visitorRepository;
        this.passCardRepository = passCardRepository;
        this.tokenValidator = tokenValidator;
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
        this.floorRepository = floorRepository;
        this.dateGenerator = dateGenerator;
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

    @Override
    public boolean checkOut(long visitId) {
        try {

            JwtTokenDTO jwtTokenDTO = tokenValidator.retrieveCurrentUserInformationFromToken();
            long user_id = jwtTokenDTO.getUser_id();

            Optional<UserEntity> userById = userRepository.findById(user_id);
            if(!userById.isPresent()) throw new VisitException("User not found");

            Optional<VisitEntity> visitById = visitRepository.findById(visitId);
            if(!visitById.isPresent()) throw new VisitException("Visit not found");

            VisitEntity visitEntity = visitById.get();
            visitEntity.setCheckoutTime(new Date());
            visitEntity.setCheckOutUserEntity(userById.get());

            VisitEntity savedVisit = visitRepository.save(visitEntity);

            PassCardEntity passCardEntity = savedVisit.getPassCardEntity();
            passCardEntity.setStatus(PassCardStatus.ACTIVE);

            passCardRepository.save(passCardEntity);

            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Page<CommonVisitResponseDTO> getAllNotCheckOut(int index, int size) {
        try {
            Pageable pageable = PageRequest.of(index, size);
            Page<VisitEntity> allNotCheckOutByDateRange = visitRepository.getAllNotCheckOutByDateRange(dateGenerator.setTime(8, 30, 0, 0), dateGenerator.setTime(18, 00, 0, 0), pageable);
            return allNotCheckOutByDateRange.map(this::mapVisitEntityToCommonVisitResponseDTO);
        } catch (Exception e) {
            throw e;
        }
    }

    private CommonVisitResponseDTO mapVisitEntityToCommonVisitResponseDTO(VisitEntity v) {

        return new CommonVisitResponseDTO(
            v.getId(),
                new VisitorDTO(v.getVisitorEntity().getId(),
                        v.getVisitorEntity().getFirstName(),
                        v.getVisitorEntity().getLastName(),
                        v.getVisitorEntity().getMobile(),
                        v.getVisitorEntity().getNic(),
                        v.getVisitorEntity().getEmail(),
                        v.getVisitorEntity().getCreatedDate()
                ),
                v.getCheckinTime(),
                null,
                v.getPurpose(),
                new UserAllDetailDTO(v.getCheckInUserEntity().getId(),
                        null,
                        v.getCheckInUserEntity().getFirstName(),
                        v.getCheckInUserEntity().getLastName(),
                        v.getCheckInUserEntity().getNic(),
                        null,
                        null,
                        null,
                        null,
                        v.getCheckInUserEntity().getRole(),
                        v.getCheckInUserEntity().getStatus()
                ),
                null,
                new FloorDTO(v.getFloorEntity().getId(),
                        v.getFloorEntity().getName(),
                        new BuildingDTO(v.getFloorEntity().getBuildingEntity().getId(),
                                v.getFloorEntity().getBuildingEntity().getName(),
                                v.getFloorEntity().getBuildingEntity().getBuildingStatus()
                        )
                ),
                new PassCardDTO(v.getPassCardEntity().getId(),
                        v.getPassCardEntity().getName(),
                        v.getPassCardEntity().getStatus()
                ),
                new EmployeeDTO(v.getEmployeeEntity().getId(),
                        v.getEmployeeEntity().getFirstName(),
                        v.getEmployeeEntity().getLastName(),
                        v.getEmployeeEntity().getNic(),
                        v.getEmployeeEntity().getEmail(),
                        v.getEmployeeEntity().getMobile(),
                        v.getEmployeeEntity().getDesignation()
                )
        );

    }

}
