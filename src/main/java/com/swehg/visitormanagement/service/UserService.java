package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.UserDTO;

public interface UserService {
    UserDTO getUserDetailsByUsername(String username);
}
