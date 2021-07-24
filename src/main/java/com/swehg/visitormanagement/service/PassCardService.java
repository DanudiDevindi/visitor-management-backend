package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.PassCardDTO;

import java.util.List;

/**
 * @author hp
 */
public interface PassCardService {
    boolean addPassCard(PassCardDTO dto);
    boolean updatePassCard(PassCardDTO dto);
    List<PassCardDTO> getAllPassCard();
    List<PassCardDTO> getActivePassCard();
    boolean deletePassCard(long id);
}
