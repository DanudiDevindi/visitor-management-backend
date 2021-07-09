package com.swehg.visitormanagement.config;

import com.swehg.visitormanagement.dto.UserDTO;
import com.swehg.visitormanagement.service.Oauth2UserService;
import com.swehg.visitormanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer extends JwtAccessTokenConverter {
//    private static final Logger LOGGER = LogManager.getLogger(CustomTokenEnhancer.class);
    private final Oauth2UserService oauth2UserService;
    private final HttpServletRequest request;
    private final BCryptPasswordEncoder encoder;
    private final UserService adminService;

    @Autowired
    public CustomTokenEnhancer(Oauth2UserService oauth2UserService, HttpServletRequest request, BCryptPasswordEncoder encoder, UserService adminService) {
        this.oauth2UserService = oauth2UserService;
        this.request = request;
        this.encoder = encoder;
        this.adminService = adminService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {


        final Map<String, Object> additionalInfo = new HashMap<>();

        User user = (User) oAuth2Authentication.getPrincipal();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User account = (User) authentication.getPrincipal();

        UserDTO userDetailsByUsername = adminService.getUserDetailsByUsername(user.getUsername());
        additionalInfo.put("user", userDetailsByUsername);
        additionalInfo.put("user_id", userDetailsByUsername.getId());

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        // set custom claims
        return super.enhance(oAuth2AccessToken, oAuth2Authentication);
    }

}
