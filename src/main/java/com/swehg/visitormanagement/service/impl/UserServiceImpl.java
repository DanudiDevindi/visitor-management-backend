package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.UserDTO;
import com.swehg.visitormanagement.entity.UserEntity;
import com.swehg.visitormanagement.repository.UserRepository;
import com.swehg.visitormanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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

}
