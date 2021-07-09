package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.VisitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<VisitorEntity, Long> {
}
