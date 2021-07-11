package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.UserAllDetailDTO;
import com.swehg.visitormanagement.dto.UserDTO;
import com.swehg.visitormanagement.entity.UserEntity;
import com.swehg.visitormanagement.enums.UserRoles;
import com.swehg.visitormanagement.enums.UserStatus;
import com.swehg.visitormanagement.exception.UserException;
import com.swehg.visitormanagement.repository.UserRepository;
import com.swehg.visitormanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean addUser(UserAllDetailDTO dto) {
        try {



            Optional<UserEntity> byNic = userRepository.findByNic(dto.getNic());
            if(byNic.isPresent()) throw new UserException("This employee already have an account");
            Optional<UserEntity> byUsername = userRepository.findByUsername(dto.getUserName());
            if(byUsername.isPresent()) throw new UserException("This username already exist");
            userRepository.save(new UserEntity(dto.getUserId(),
                    dto.getUserName(),
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getNic(),
                    dto.getEmail(),
                    dto.getMobile(),
                    bCryptPasswordEncoder.encode(dto.getPassword()),
                    new Date(),
                    UserRoles.RECEP,
                    UserStatus.ACTIVE
                    ));
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean updateUser(UserAllDetailDTO dto) {
        try {
            Optional<UserEntity> userById = userRepository.findById(dto.getUserId());
            if(!userById.isPresent()) throw new UserException("Receptionist account not found");
            UserEntity userEntity = userById.get();
            if(!userEntity.getUsername().equals(dto.getUserName())) {
                Optional<UserEntity> byUsername = userRepository.findByUsername(dto.getUserName());
                if(!byUsername.isPresent()) throw new UserException("This username already exist");
            }
            userEntity.setUsername(dto.getUserName());
            userEntity.setFirstName(dto.getFirstName());
            userEntity.setLastName(dto.getLastName());
            userEntity.setNic(dto.getNic());
            userEntity.setEmail(dto.getEmail());

            if(dto.getPassword()!=null) {
                userEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            }

            userEntity.setStatus(dto.getStatus());

            userRepository.save(userEntity);

            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public UserDTO getUserDetailsByUsername(String username) {
        try {

            Optional<UserEntity> byUsername = userRepository.findByUsername(username);
            if(!byUsername.isPresent()) throw new RuntimeException();
            UserEntity userEntity = byUsername.get();
            userEntity.setPassword(null);
            return modelMapper.map(userEntity, UserDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<UserAllDetailDTO> getAllUser(String word) {
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
            throw e;
        }
    }

}
