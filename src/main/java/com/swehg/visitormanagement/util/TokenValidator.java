package com.swehg.visitormanagement.util;

import com.google.gson.Gson;
import com.swehg.visitormanagement.dto.JwtTokenDTO;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenValidator {

    public JwtTokenDTO decode(String token){

        Jwt decodedJwt = JwtHelper.decode(token);
        String claims = decodedJwt.getClaims();
        Gson gson = new Gson();

        return gson.fromJson(claims, JwtTokenDTO.class);
    }

    public JwtTokenDTO retrieveCurrentUserInformationFromToken(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").split(" ")[1];

        return decode(token);
    }

}
