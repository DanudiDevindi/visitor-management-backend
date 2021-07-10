package com.swehg.visitormanagement.service;

import com.swehg.visitormanagement.dto.BuildingDTO;
import com.swehg.visitormanagement.dto.VisitorDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author hp
 */
public interface VisitorService {
    VisitorDTO filterVisitorByNic(String nic);
    Page<VisitorDTO> filterVisitor(String word, int index, int size);
}
