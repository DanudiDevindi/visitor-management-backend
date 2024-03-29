package com.swehg.visitormanagement.service.impl;

import com.swehg.visitormanagement.dto.PassCardDTO;
import com.swehg.visitormanagement.entity.PassCardEntity;
import com.swehg.visitormanagement.enums.PassCardStatus;
import com.swehg.visitormanagement.exception.VisitorException;
import com.swehg.visitormanagement.repository.PassCardRepository;
import com.swehg.visitormanagement.service.PassCardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author hp
 */

@Service
@Log4j2
public class PassCardServiceImpl implements PassCardService {

    private final PassCardRepository passCardRepository;

    @Autowired
    public PassCardServiceImpl(PassCardRepository passCardRepository) {
        this.passCardRepository = passCardRepository;
    }

    @Override
    public boolean addPassCard(PassCardDTO dto) {
        log.info("Execute addPassCard: dto: " + dto);
        try {
            Optional<PassCardEntity> byName = passCardRepository.findByName(dto.getName());
            if(byName.isPresent()) throw new VisitorException("Pass card already existing with this name - " +
                    dto.getName() + ". Please, use another pass card name.");
            passCardRepository.save(new PassCardEntity(dto.getName(), PassCardStatus.ACTIVE));
            return true;
        } catch (Exception e) {
            log.error("Execute addPassCard: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean updatePassCard(PassCardDTO dto) {
        log.info("Execute updatePassCard: dto: " + dto);
        try {
            Optional<PassCardEntity> byId = passCardRepository.findById(dto.getPassCardId());
            if(!byId.isPresent()) throw new VisitorException("Pass card not found");
            PassCardEntity passCardEntity = byId.get();
            if(!passCardEntity.getName().equals(dto.getName())) {
                Optional<PassCardEntity> byName = passCardRepository.findByName(dto.getName());
                if(byName.isPresent()) throw new VisitorException("Pass card already existing with this name - " +
                        dto.getName() + ". Please, use another pass card name.");
            }
            passCardEntity.setName(dto.getName());
            passCardEntity.setStatus(dto.getStatus());
            passCardRepository.save(passCardEntity);
            return true;
        } catch (Exception e) {
            log.error("Execute updatePassCard: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PassCardDTO> getAllPassCard() {
        log.info("Execute getAllPassCard:");
        List<PassCardDTO> allList = new ArrayList<>();
        try {
            List<PassCardEntity> all = passCardRepository.findAll();
            for (PassCardEntity p : all) {
                allList.add(new PassCardDTO(p.getId(), p.getName(), p.getStatus()));
            }
            return allList;
        } catch (Exception e) {
            log.error("Execute getAllPassCard: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PassCardDTO> getActivePassCard() {
        log.info("Execute getActivePassCard:");
        List<PassCardDTO> allActiveList = new ArrayList<>();
        try {
            List<PassCardEntity> all = passCardRepository.findAllByStatus(PassCardStatus.ACTIVE);
            for (PassCardEntity p : all) {
                allActiveList.add(new PassCardDTO(p.getId(), p.getName(), p.getStatus()));
            }
            return allActiveList;
        } catch (Exception e) {
            log.error("Execute getActivePassCard: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean deletePassCard(long id) {
        log.info("Execute deletePassCard: id: " + id);
        try {
            Optional<PassCardEntity> byId = passCardRepository.findById(id);
            if(!byId.isPresent()) throw new VisitorException("Pass card not found");
            passCardRepository.delete(byId.get());
            return true;
        } catch (Exception e) {
            log.error("Execute loadUserByUsername: " + e.getMessage());
            throw e;
        }
    }
}
