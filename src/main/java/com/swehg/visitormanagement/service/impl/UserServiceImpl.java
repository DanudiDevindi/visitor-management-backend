package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.UserAllDetailDTO;
import com.swehg.visitormanagement.dto.UserDTO;
import com.swehg.visitormanagement.entity.UserEntity;
import com.swehg.visitormanagement.enums.UserRoles;
import com.swehg.visitormanagement.enums.UserStatus;
import com.swehg.visitormanagement.exception.UserException;
import com.swehg.visitormanagement.repository.UserRepository;
import com.swehg.visitormanagement.service.UserService;
import com.swehg.visitormanagement.util.EmailSender;
import com.swehg.visitormanagement.util.MobileValidator;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSender emailSender;
    private final MobileValidator mobileValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, EmailSender emailSender, MobileValidator mobileValidator) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
        this.mobileValidator = mobileValidator;
    }

    @Override
    public boolean addUser(UserAllDetailDTO dto) {
        log.info("Execute addUser: dto: " + dto);
        try {

            Optional<UserEntity> byNic = userRepository.findByNic(dto.getNic());
            if(byNic.isPresent()) throw new UserException("This employee already have an account");

            String mobileStandardFormat = mobileValidator.getMobileStandardFormat(dto.getMobile());

            if(mobileStandardFormat==null) throw new UserException("Invalid mobile number");

            Optional<UserEntity> byMobile = userRepository.findByMobile(mobileStandardFormat);
            if(byMobile.isPresent()) throw new UserException("Another account already exist with this mobile number");

            Optional<UserEntity> byEmail = userRepository.findByEmail(dto.getEmail());
            if(byEmail.isPresent()) throw new UserException("Another account already exist with this email");
            Optional<UserEntity> byUsername = userRepository.findByUsername(dto.getUserName());
            if(byUsername.isPresent()) throw new UserException("This username already exist");
            userRepository.save(new UserEntity(dto.getUserId(),
                    dto.getUserName(),
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getNic(),
                    dto.getEmail(),
                    mobileStandardFormat,
                    bCryptPasswordEncoder.encode(dto.getPassword()),
                    new Date(),
                    UserRoles.RECEP,
                    UserStatus.ACTIVE
                    ));
            return true;
        } catch (Exception e) {
            log.error("Execute addUser: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean updateUser(UserAllDetailDTO dto) {
        log.info("Execute updateUser: dto: " + dto);
        try {
            Optional<UserEntity> userById = userRepository.findById(dto.getUserId());
            if(!userById.isPresent()) throw new UserException("Receptionist account not found");
            UserEntity userEntity = userById.get();
            if(!userEntity.getUsername().equals(dto.getUserName())) {
                Optional<UserEntity> byUsername = userRepository.findByUsername(dto.getUserName());
                if(byUsername.isPresent()) throw new UserException("This username already exist");
            }
            if(!userEntity.getNic().equals(dto.getNic())){
                Optional<UserEntity> byNic = userRepository.findByNic(dto.getNic());
                if(byNic.isPresent()) throw new UserException("Another account already exist with this NIC");
            }

            String mobileStandardFormat = mobileValidator.getMobileStandardFormat(dto.getMobile());

            if(mobileStandardFormat==null) throw new UserException("Invalid mobile number");

            if(!userEntity.getMobile().equals(mobileStandardFormat)) {
                Optional<UserEntity> byMobile = userRepository.findByMobile(mobileStandardFormat);
                if(byMobile.isPresent()) throw new UserException("Another account already exist with this mobile number");
            }

            if(!userEntity.getEmail().equals(dto.getEmail())){
                Optional<UserEntity> byEmail = userRepository.findByEmail(dto.getEmail());
                if(byEmail.isPresent()) throw new UserException("Another account already exist with this email");
            }
            userEntity.setUsername(dto.getUserName());
            userEntity.setFirstName(dto.getFirstName());
            userEntity.setLastName(dto.getLastName());
            userEntity.setNic(dto.getNic());
            userEntity.setEmail(dto.getEmail());
            userEntity.setMobile(mobileStandardFormat);

            if(dto.getPassword()!=null) {
                userEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            }

            userEntity.setStatus(dto.getStatus());

            userRepository.save(userEntity);

            return true;
        } catch (Exception e) {
            log.error("Execute updateUser: " + e.getMessage());
            throw e;
        }
    }

    public UserDTO getUserDetailsByUsername(String username) {
        log.info("Execute getUserDetailsByUsername: username: " + username);
        try {

            Optional<UserEntity> byUsername = userRepository.findByUsername(username);
            if(!byUsername.isPresent()) throw new RuntimeException();
            UserEntity userEntity = byUsername.get();
            userEntity.setPassword(null);
            return modelMapper.map(userEntity, UserDTO.class);

        } catch (Exception e) {
            log.error("Execute getUserDetailsByUsername: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<UserAllDetailDTO> getAllUser(String word) {
        log.info("Execute getAllUser: word: " + word);
        try {

            List<UserEntity> allUsersExceptStatus = userRepository.getAllUsersExceptStatus(
                    (!word.equals(""))?word:null,
                    UserStatus.DELETED);
            List<UserAllDetailDTO> all = new ArrayList<>();
            for (UserEntity u : allUsersExceptStatus) {
                all.add(new UserAllDetailDTO(u.getId(),
                        u.getUsername(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getNic(),
                        u.getEmail(),
                        u.getMobile(),
                        null,
                        u.getCreatedDate(),
                        u.getRole(),
                        u.getStatus()));
            }

            return all;

        } catch (Exception e) {
            log.error("Execute getAllUser: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean sendEmail(String email,String sub, String body) {
        try {

            emailSender.send(email, sub, body);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

}
