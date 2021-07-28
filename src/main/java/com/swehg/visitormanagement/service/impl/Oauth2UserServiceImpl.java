package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.entity.UserEntity;
import com.swehg.visitormanagement.repository.UserRepository;
import com.swehg.visitormanagement.service.Oauth2UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service(value = "userService")
@Log4j2
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class Oauth2UserServiceImpl implements Oauth2UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public Oauth2UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Execute loadUserByUsername: s: " + s);
        try {

                Optional<UserEntity> byUsername = userRepository.findByUsername(s);
                if(!byUsername.isPresent()) throw new RuntimeException();
                return new org.springframework.security.core.userdetails.User(byUsername.get().getUsername(), byUsername.get().getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+byUsername.get().getRole().toString())));

        } catch (Exception e) {
            log.error("Execute loadUserByUsername: " + e.getMessage());
            throw e;
        }
    }
}
