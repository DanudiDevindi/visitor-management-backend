package com.swehg.visitormanagement.repository;

import com.swehg.visitormanagement.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<VisitEntity, Long> {
}
