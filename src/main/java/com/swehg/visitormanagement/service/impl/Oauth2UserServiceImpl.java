package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.constant.OAuth2Constant;
import com.swehg.visitormanagement.entity.UserEntity;
import com.swehg.visitormanagement.repository.UserRepository;
import com.swehg.visitormanagement.service.Oauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service(value = "userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class Oauth2UserServiceImpl implements Oauth2UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public Oauth2UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {

                Optional<UserEntity> byUsername = userRepository.findByUsername(s);
                if(!byUsername.isPresent()) throw new RuntimeException();
                return new org.springframework.security.core.userdetails.User(byUsername.get().getUsername(), byUsername.get().getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(byUsername.get().getRole().toString())));

        } catch (Exception e) {
            throw e;
        }
    }
}
