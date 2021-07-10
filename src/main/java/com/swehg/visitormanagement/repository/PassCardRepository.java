package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.PassCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassCardRepository extends JpaRepository<PassCardEntity, Long> {
}
