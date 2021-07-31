package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.*;
import com.swehg.visitormanagement.dto.request.CheckInRequestDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseForTableDTO;
import com.swehg.visitormanagement.entity.*;
import com.swehg.visitormanagement.enums.HistorySearchTypes;
import com.swehg.visitormanagement.enums.PassCardStatus;
import com.swehg.visitormanagement.exception.VisitException;
import com.swehg.visitormanagement.repository.*;
import com.swehg.visitormanagement.service.VisitService;
import com.swehg.visitormanagement.util.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@Log4j2
public class VisitServiceImpl implements VisitService {

    private final EmployeeRepository employeeRepository;
    private final VisitorRepository visitorRepository;
    private final PassCardRepository passCardRepository;
    private final TokenValidator tokenValidator;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final FloorRepository floorRepository;
    private final DateGenerator dateGenerator;
    private final EmailTemplateGen emailTemplateGen;
    private final EmailSender emailSender;
    private final MobileValidator mobileValidator;

    @Autowired
    public VisitServiceImpl(EmployeeRepository employeeRepository, VisitorRepository visitorRepository, PassCardRepository passCardRepository, TokenValidator tokenValidator, UserRepository userRepository, VisitRepository visitRepository, FloorRepository floorRepository, DateGenerator dateGenerator, EmailTemplateGen emailTemplateGen, EmailSender emailSender, MobileValidator mobileValidator) {
        this.employeeRepository = employeeRepository;
        this.visitorRepository = visitorRepository;
        this.passCardRepository = passCardRepository;
        this.tokenValidator = tokenValidator;
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
        this.floorRepository = floorRepository;
        this.dateGenerator = dateGenerator;
        this.emailTemplateGen = emailTemplateGen;
        this.emailSender = emailSender;
        this.mobileValidator = mobileValidator;
    }

    /**
     * checkin service method
     *
     * @param dto
     * @return true or false
     */
    @Override
    public boolean checkIn(CheckInRequestDTO dto) {
        log.info("Execute checkIn: dto: " + dto);
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
                    visitorEntity = checkVisitorDataForUpdate(visitorById.get(), c);
                } else {
                    System.out.println("H1");

                    String mobileStandardFormat = mobileValidator.getMobileStandardFormat(c.getMobile());
                    if(mobileStandardFormat==null) throw new VisitException("Invalid visitor mobile number");

                    visitorEntity = visitorRepository.save(new VisitorEntity(c.getVisitorFirstName(), c.getVisitorLastName(), c.getMobile(), c.getNic(), c.getEmail(), new Date()));
                }

                Optional<PassCardEntity> passCardById = passCardRepository.findById(c.getPassCardId());
                if(!passCardById.isPresent()) throw new VisitException("Pass card not found");
                PassCardEntity passCardEntity = passCardById.get();
                if(passCardEntity.getStatus().equals(PassCardStatus.RESERVED)) throw new VisitException("Pass card" + passCardEntity.getName() + " already reserved");
                passCardEntity.setStatus(PassCardStatus.RESERVED);
                PassCardEntity savedPassCard = passCardRepository.save(passCardEntity);
                VisitEntity save = visitRepository.save(new VisitEntity(visitorEntity, new Date(), null, dto.getPurpose(), userById.get(), null, floorById.get(), savedPassCard, employeeById.get()));
                emailSender.send(save.getEmployeeEntity().getEmail(), "Visito: New Visitor", emailTemplateGen.getCheckInEmailTemplate(save));
            }

            return true;

        } catch (Exception e) {
            log.error("Execute checkIn: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean checkOut(long visitId) {
        log.info("Execute checkOut: visitId: " + visitId);
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

            emailSender.send(savedVisit.getEmployeeEntity().getEmail(), "Visito: Visitor checked out", emailTemplateGen.getCheckedOut(savedVisit));

            return true;

        } catch (Exception e) {
            log.error("Execute checkOut: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<CommonVisitResponseDTO> getAllNotCheckOut(String word, int index, int size) {
        log.info("Execute getAllNotCheckOut: word: " + word + ", index: " + index + ", size: " + size);
        try {
            Pageable pageable = PageRequest.of(index, size);
            Page<VisitEntity> allNotCheckOutByDateRange = visitRepository.getAllNotCheckOutByDateRange(word, dateGenerator.setTime(3, 00, 0, 0), dateGenerator.setTime(18, 29, 59, 0), pageable);
            return allNotCheckOutByDateRange.map(this::mapVisitEntityToCommonVisitResponseDTO);
        } catch (Exception e) {
            log.error("Execute getAllNotCheckOut: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<CommonVisitResponseDTO> getAllOverdueCheckin(String word, int index, int size) {
        log.info("Execute getAllOverdueCheckin: word: " + word + ", index: " + index + ", size: " + size);
        try {
            Pageable pageable = PageRequest.of(index, size);
            Page<VisitEntity> allNotCheckOutByDateRange = visitRepository.getAllOverdueCheckinByDateRange(word, new Date(), dateGenerator.setTime(18, 00, 0, 0), pageable);
            return allNotCheckOutByDateRange.map(this::mapVisitEntityToCommonVisitResponseDTO);
        } catch (Exception e) {
            log.error("Execute getAllOverdueCheckin: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<CommonVisitResponseDTO> getHistory(HistorySearchTypes type, String word, String startDate, String endDate, int index, int size) {
        log.info("Execute getHistory: type: " + type + ", word: " + word +", startDate: " + startDate + ", endDate: " + endDate +", index: " + index + ", size: " + size);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            Pageable pageable = PageRequest.of(index, size);

            Page<VisitEntity> allVisitHistory =  null;

            if(startDate.equals("") || startDate==null || endDate.equals("") || endDate==null) {
                allVisitHistory = visitRepository.getVisitHistory(word, new Date(), pageable);
            } else {

                Date sDate = dateGenerator.convertToLocalTime(formatter.parse(startDate + " 00:00:00"));
                Date eDate = dateGenerator.convertToLocalTime(formatter.parse(endDate + " 23:59:59"));

                if(type.equals(HistorySearchTypes.ALL)) {
                    allVisitHistory = visitRepository.getAllVisitHistory(word, sDate, eDate, pageable);
                }

                if(type.equals(HistorySearchTypes.NOTCHECKOUT)) {
                    allVisitHistory = visitRepository.getAllNotCheckOutVisitHistory(word, sDate, eDate, pageable);
                }

                if(type.equals(HistorySearchTypes.CHECKOUT)) {
                    allVisitHistory = visitRepository.getAllCheckedOutVisitHistory(word, sDate, eDate,  pageable);
                }
            }

            return allVisitHistory.map(this::mapVisitEntityToCommonVisitResponseDTO);

        } catch (ParseException e) {
            log.error("Execute getHistory: " + e.getMessage());
            throw new VisitException("Something went wrong");
        } catch (Exception e) {
            log.error("Execute getHistory: " + e.getMessage());
            throw e;
        }
    }

    private CommonVisitResponseForTableDTO mapVisitEntityToCommonVisitResponseDTO2(VisitEntity v) {

        return new CommonVisitResponseForTableDTO(
                v.getId(),
                v.getPassCardEntity().getName(),
                v.getCheckinTime(),
                v.getVisitorEntity().getNic(),
                v.getVisitorEntity().getFirstName(),
                v.getVisitorEntity().getLastName(),
                v.getVisitorEntity().getMobile(),
                v.getVisitorEntity().getEmail(),
                v.getEmployeeEntity().getFirstName() + " " +  v.getEmployeeEntity().getLastName()
        );

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
                dateGenerator.convertToLocalTime(v.getCheckinTime()),
                v.getCheckoutTime()!=null?dateGenerator.convertToLocalTime(v.getCheckoutTime()):null,
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
                        ),
                        v.getFloorEntity().getFloorStatus()
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


    private VisitorEntity checkVisitorDataForUpdate(VisitorEntity v, CheckInVisitorDTO c) {
        if(!v.getFirstName().equals(c.getVisitorFirstName())) {
            v.setFirstName(c.getVisitorFirstName());
        }

        if(!v.getLastName().equals(c.getVisitorLastName())) {
            v.setLastName(c.getVisitorLastName());
        }

        if(!v.getMobile().equals(c.getMobile())) {
            v.setMobile(c.getMobile());
        }

        if(!v.getEmail().equals(c.getEmail())) {
            v.setEmail(c.getEmail());
        }

        return v;
    }


}
