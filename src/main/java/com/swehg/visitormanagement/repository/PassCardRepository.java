package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.PassCardEntity;
import com.swehg.visitormanagement.enums.PassCardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassCardRepository extends JpaRepository<PassCardEntity, Long> {

    Optional<PassCardEntity> findByName(String name);
    List<PassCardEntity> findAllByStatus(PassCardStatus status);
}
