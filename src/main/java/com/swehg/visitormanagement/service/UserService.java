package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.UserAllDetailDTO;
import com.swehg.visitormanagement.dto.UserDTO;

import java.util.List;

public interface UserService {
    boolean addUser(UserAllDetailDTO dto);
    boolean updateUser(UserAllDetailDTO dto);
    UserDTO getUserDetailsByUsername(String username);
    List<UserAllDetailDTO> getAllUser(String word);

    boolean sendEmail(String email, String sub, String body);

}