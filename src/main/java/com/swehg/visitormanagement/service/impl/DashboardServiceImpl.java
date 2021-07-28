package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.response.DashboardResponseDTO;
import com.swehg.visitormanagement.entity.VisitorEntity;
import com.swehg.visitormanagement.repository.VisitRepository;
import com.swehg.visitormanagement.repository.VisitorRepository;
import com.swehg.visitormanagement.service.DashboardService;
import com.swehg.visitormanagement.util.DateGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hp
 */

@Service
@Log4j2
public class DashboardServiceImpl implements DashboardService {

    private final VisitorRepository visitorRepository;
    private final VisitRepository visitRepository;
    private final DateGenerator dateGenerator;

    @Autowired
    public DashboardServiceImpl(VisitorRepository visitorRepository, VisitRepository visitRepository, DateGenerator dateGenerator) {
        this.visitorRepository = visitorRepository;
        this.visitRepository = visitRepository;
        this.dateGenerator = dateGenerator;
    }

    @Override
    public DashboardResponseDTO getDashboardData() {
       try {
           log.info("Execute getDashboardData:");

           int checkin = visitRepository.getAllNotCheckOutByDateRangeCount("", dateGenerator.setTime(8, 30, 0, 0), dateGenerator.setTime(18, 00, 0, 0));
           int overdue = visitRepository.getAllOverdueCheckinByDateRangeCount("", dateGenerator.setTime(8, 30, 0, 0), dateGenerator.setTime(18, 00, 0, 0));
           int visits = visitRepository.getAllVisitCount(dateGenerator.setTime(8, 30, 0, 0), dateGenerator.setTime(18, 00, 0, 0));
           List<VisitorEntity> all = visitorRepository.findAll();
           int visitor = all.size();

           return new DashboardResponseDTO(checkin,overdue,visits,visitor);

       } catch (Exception e) {
           log.error("Execute getDashboardData: " + e.getMessage());
           throw e;
       }
    }
}
