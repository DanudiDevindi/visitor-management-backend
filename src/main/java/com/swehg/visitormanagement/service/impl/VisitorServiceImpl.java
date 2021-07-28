package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.BuildingDTO;
import com.swehg.visitormanagement.dto.VisitorDTO;
import com.swehg.visitormanagement.dto.response.CommonVisitResponseDTO;
import com.swehg.visitormanagement.entity.VisitEntity;
import com.swehg.visitormanagement.entity.VisitorEntity;
import com.swehg.visitormanagement.enums.PassCardStatus;
import com.swehg.visitormanagement.exception.VisitorException;
import com.swehg.visitormanagement.repository.VisitRepository;
import com.swehg.visitormanagement.repository.VisitorRepository;
import com.swehg.visitormanagement.service.VisitorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hp
 */

@Service
@Log4j2
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitRepository visitRepository;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository, VisitRepository visitRepository) {
        this.visitorRepository = visitorRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    public VisitorDTO filterVisitorByNic(String nic) {
        log.info("Execute filterVisitorByNic: nic: " + nic);
        try {
            Optional<VisitorEntity> visitorByNic = visitorRepository.findByNic(nic);
            if(!visitorByNic.isPresent()) return null;
            List<VisitEntity> visitEntities = visitRepository.checkVisitorVisit(visitorByNic.get(), PassCardStatus.RESERVED);
            if(!visitEntities.isEmpty()) throw new VisitorException(716,"Visitor already checked in with Pass Card: " + visitEntities.get(0).getPassCardEntity().getName());
            return new VisitorDTO(visitorByNic.get().getId(),
                    visitorByNic.get().getFirstName(),
                    visitorByNic.get().getLastName(),
                    visitorByNic.get().getMobile(),
                    visitorByNic.get().getNic(),
                    visitorByNic.get().getEmail(),
                    visitorByNic.get().getCreatedDate());
        } catch (Exception e) {
            log.error("Execute filterVisitorByNic: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Page<VisitorDTO> filterVisitor(String word, int index, int size) {
        log.info("Execute filterVisitor: word: " + word + ", index: " + index + ", size: " + size);
        try {

            Pageable pageable = PageRequest.of(index, size);
            Page<VisitorEntity> visitorEntities = visitorRepository.filterVisitor(word, pageable);

            return visitorEntities.map(this::mapVisitorEntityToVisitorDTO);

        } catch (Exception e) {
            log.error("Execute filterVisitor: " + e.getMessage());
            throw e;
        }
    }

    private VisitorDTO mapVisitorEntityToVisitorDTO(VisitorEntity v) {
        return new VisitorDTO(v.getId(),
                v.getFirstName(),
                v.getLastName(),
                v.getMobile(),
                v.getNic(),
                v.getEmail(),
                v.getCreatedDate());
    }
}
